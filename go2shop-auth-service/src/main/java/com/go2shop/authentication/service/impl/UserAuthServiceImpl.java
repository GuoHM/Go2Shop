package com.go2shop.authentication.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.go2shop.authentication.model.AuthorityName;
import com.go2shop.authentication.model.UserRegisterDTO;
import com.go2shop.authentication.model.UserTokenDTO;
import com.go2shop.authentication.model.entity.Authority;
import com.go2shop.authentication.model.entity.SecurityUser;
import com.go2shop.authentication.repository.AuthorityRepository;
import com.go2shop.authentication.repository.SecurityUserRepository;
import com.go2shop.authentication.service.UserAuthService;
import com.go2shop.authentication.service.feign.UserService;
import com.go2shop.authentication.service.mapper.UserRegisterMapper;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.common.model.ActiveStatus;
import com.go2shop.model.user.UserDTO;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRegisterMapper userRegisterMapper;

	@Autowired
	private SecurityUserRepository securityUserRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserTokenDTO handleLoginSuccess(OAuth2AccessToken userToken) {
		UserTokenDTO oauth2TokenDTO = new UserTokenDTO();
		oauth2TokenDTO.setToken(userToken.getValue());
		oauth2TokenDTO.setRefreshToken(userToken.getRefreshToken().getValue());
		oauth2TokenDTO.setTokenHead("Bearer ");
		oauth2TokenDTO.setExpiresIn(userToken.getExpiresIn());
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
		try {
			SecurityUser securityUser = userRegisterMapper.toSecurityUser(userRegister);
			securityUser.setAuthorities(findAuthorityByAuthorityName(userRegister.getType()));
			securityUser.setUserId(response.getBody().getId());
			securityUser.setEnabled(ActiveStatus.ACTIVE);
			securityUser.setPassword(passwordEncoder.encode(securityUser.getPassword()));
			securityUserRepository.saveAndFlush(securityUser);
		} catch (Exception e) {
			if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
				throw new BusinessException(EmBusinessError.USER_USERNAME_EXIST);
			}
		}

		return response.getBody();
	}

	private List<Authority> findAuthorityByAuthorityName(String type) {
		AuthorityName authorityName = AuthorityName.getByString(type);
		return Arrays.asList(authorityRepository.findByName(authorityName));
	}

}
