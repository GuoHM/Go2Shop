package com.go2shop.authentication.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.go2shop.authentication.model.UserTokenDTO;

public interface UserAuthService {
	
	UserTokenDTO handleLoginSuccess(OAuth2AccessToken userToken);

}
