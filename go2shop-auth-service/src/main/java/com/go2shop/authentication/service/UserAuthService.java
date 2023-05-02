package com.go2shop.authentication.service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.go2shop.authentication.model.UserRegisterDTO;
import com.go2shop.authentication.model.UserTokenDTO;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.user.UserDTO;
import com.go2shop.model.user.UserLoginDTO;

public interface UserAuthService {
	
	boolean verifyIf2faIsRequired(String username) throws BusinessException;
	
	boolean loginUser(UserLoginDTO userDetails) throws BusinessException;
	
	UserTokenDTO handleLoginSuccess(OAuth2AccessToken userToken, String username) throws BusinessException;
	
	UserDTO registerUser(UserRegisterDTO userRegister) throws BusinessException;

	String updateUser2fa(UserRegisterDTO userRegister) throws BusinessException;
}
