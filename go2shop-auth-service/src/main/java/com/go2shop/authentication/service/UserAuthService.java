package com.go2shop.authentication.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.go2shop.authentication.model.UserRegisterDTO;
import com.go2shop.authentication.model.UserTokenDTO;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.user.UserDTO;

public interface UserAuthService {
	
	UserTokenDTO handleLoginSuccess(OAuth2AccessToken userToken);
	
	UserDTO registerUser(UserRegisterDTO userRegister) throws BusinessException;

}
