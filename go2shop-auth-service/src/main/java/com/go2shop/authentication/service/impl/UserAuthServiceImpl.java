package com.go2shop.authentication.service.impl;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.go2shop.authentication.model.UserTokenDTO;
import com.go2shop.authentication.service.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Override
	public UserTokenDTO handleLoginSuccess(OAuth2AccessToken userToken) {
		UserTokenDTO oauth2TokenDTO = new UserTokenDTO();
		oauth2TokenDTO.setToken(userToken.getValue());
		oauth2TokenDTO.setRefreshToken(userToken.getRefreshToken().getValue());
		oauth2TokenDTO.setTokenHead("Bearer ");
		oauth2TokenDTO.setExpiresIn(userToken.getExpiresIn());
		return oauth2TokenDTO;
	}

}
