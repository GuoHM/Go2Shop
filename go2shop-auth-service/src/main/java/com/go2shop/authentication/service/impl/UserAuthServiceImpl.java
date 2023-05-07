package com.go2shop.authentication.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jboss.aerogear.security.otp.Totp;
import org.jboss.aerogear.security.otp.api.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.go2shop.authentication.config.Encryptor;
import com.go2shop.authentication.model.AuthorityName;
import com.go2shop.authentication.model.UserRegisterDTO;
import com.go2shop.authentication.model.UserTokenDTO;
import com.go2shop.authentication.model.entity.Authority;
import com.go2shop.authentication.model.entity.SecurityUser;
import com.go2shop.authentication.repository.AuthorityRepository;
import com.go2shop.authentication.repository.SecurityUserRepository;
import com.go2shop.authentication.service.UserAuthService;
import com.go2shop.authentication.service.feign.ShoppingCartService;
import com.go2shop.authentication.service.feign.UserService;
import com.go2shop.authentication.service.mapper.UserRegisterMapper;
import com.go2shop.authentication.util.PasswordUtil;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.common.model.ActiveStatus;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.user.UserDTO;
import com.go2shop.model.user.UserLoginDTO;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	public static final String QR_PREFIX = "https://chart.googleapis.com/chart?chs=200x200&chld=M%%7C0&cht=qr&chl=";
	public static final String APP_NAME = "Go2Shop";
	
	@Autowired
	private UserService userService;

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	@Autowired
	private UserRegisterMapper userRegisterMapper;

	@Autowired
	private SecurityUserRepository securityUserRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private StringEncryptor stringEncryptor = new Encryptor().createPBEDefault();
	
	@Value("${security.secret-key}")
	private String stringOutputType;
	
	@Override
	public boolean verifyIf2faIsRequired(String username) throws BusinessException {
		if(StringUtils.isNotBlank(username)) {
			SecurityUser user = this.securityUserRepository.findByUsername(username);
			if(user != null) {
				return user.getAuthEnabled();
			}
		}
		throw new BusinessException(EmBusinessError.USER_NOT_EXIST); 
	}
	
	@Override
	public boolean loginUser(UserLoginDTO userDetails) throws BusinessException {
		if(userDetails != null) {
			SecurityUser securityUser = securityUserRepository.findByUsername(userDetails.getUsername());
			if (securityUser == null) {
				throw new BusinessException(EmBusinessError.USER_NOT_EXIST); 
			}
			if (securityUser.getAuthEnabled() && StringUtils.isNotBlank(userDetails.getOtp())) {
				final String verificationCode = PasswordUtil.decrypt(userDetails.getOtp(), stringOutputType);
				final Totp totp = new Totp(stringEncryptor.decrypt(securityUser.getSecret()));
				if (!totp.verify(verificationCode)) {
					throw new BusinessException(EmBusinessError.INVALID_OTP);
				}
			}
			return true;
		}
		throw new BusinessException(EmBusinessError.USER_NOT_EXIST); 
	}
	
	@Override
	public UserTokenDTO handleLoginSuccess(OAuth2AccessToken userToken, String username) throws BusinessException {
		UserTokenDTO oauth2TokenDTO = new UserTokenDTO();
		oauth2TokenDTO.setToken(userToken.getValue());
		oauth2TokenDTO.setRefreshToken(userToken.getRefreshToken().getValue());
		oauth2TokenDTO.setTokenHead("Bearer ");
		oauth2TokenDTO.setExpiresIn(userToken.getExpiresIn());
		SecurityUser securityUser = securityUserRepository.findByUsername(username);
		if (securityUser == null) {
			throw new BusinessException(EmBusinessError.USER_NOT_EXIST); 
		}
		oauth2TokenDTO.setAuthEnabled(securityUser.getAuthEnabled());
		oauth2TokenDTO.setUserId(securityUser.getUserId());
		ResponseEntity<ShoppingCartDTO> response = shoppingCartService.getShoppingCartByUserId(securityUser.getUserId());
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
		} else if(!response.hasBody() || (response.hasBody() && response.getBody() == null)) {
			throw new BusinessException(EmBusinessError.CART_NOT_EXIST);
		} else {
			ShoppingCartDTO cart = response.getBody();
			if(cart != null) {
				oauth2TokenDTO.setCartId(cart.getId());
			}
		}
		return oauth2TokenDTO;
	}

	@Override
	public UserDTO registerUser(UserRegisterDTO userRegister) throws BusinessException {
		UserDTO user = userRegisterMapper.toUserDto(userRegister);
		user.setExpiry("N");
		ResponseEntity<UserDTO> response = userService.register(user);
		if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
		}
		SecurityUser securityUser = userRegisterMapper.toSecurityUser(userRegister);
		try {
			securityUser.setAuthorities(findAuthorityByAuthorityName(userRegister.getType()));
			securityUser.setUserId(response.getBody().getId());
			securityUser.setEnabled(ActiveStatus.ACTIVE);
			securityUser.setPassword(passwordEncoder.encode(PasswordUtil.decrypt(securityUser.getPassword(), stringOutputType)));
			securityUser.setAuthEnabled(userRegister.getAuthEnabled());
			securityUser.setSecret(stringEncryptor.encrypt(securityUser.getSecret()));
			securityUser = securityUserRepository.saveAndFlush(securityUser);
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new BusinessException(EmBusinessError.USER_USERNAME_EXIST);
			}
		}
		user = response.getBody();
		try {
			user.setQrCode(this.generateQRUrl(securityUser));			
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
		}
		return user;
	}

	@Override
	public String updateUser2fa(UserRegisterDTO userRegister) throws BusinessException {
		if(userRegister != null && StringUtils.isNotBlank(userRegister.getUsername())) {
			SecurityUser securityUser = securityUserRepository.findByUsername(userRegister.getUsername());
			if(securityUser == null) {
				throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
			}
			if(Boolean.compare(userRegister.getAuthEnabled(), securityUser.getAuthEnabled()) != 0) {
				securityUser.setAuthEnabled(userRegister.getAuthEnabled());
				if(userRegister.getAuthEnabled()) {
					securityUser.setSecret(stringEncryptor.encrypt(Base32.random()));
				} else {
					securityUser.setSecret(null);
				}
				securityUser = securityUserRepository.saveAndFlush(securityUser);
				if(securityUser.getAuthEnabled()) {
					try {
						return this.generateQRUrl(securityUser);			
					} catch (UnsupportedEncodingException e) {
						throw new BusinessException(EmBusinessError.SERVICE_NOT_AVAILABLE);
					}					
				}
			}
		}
		return StringUtils.EMPTY;
	}
	
	private List<Authority> findAuthorityByAuthorityName(String type) {
		AuthorityName authorityName = AuthorityName.getByString(type);
		return Arrays.asList(authorityRepository.findByName(authorityName));
	}

	private String generateQRUrl(SecurityUser securityUser) throws UnsupportedEncodingException {
	    return QR_PREFIX + URLEncoder.encode(String.format(
	      "otpauth://totp/%s:%s?secret=%s&issuer=%s", 
	      APP_NAME, securityUser.getUsername(), stringEncryptor.decrypt(securityUser.getSecret()), APP_NAME),
	      "UTF-8");
	}
}
