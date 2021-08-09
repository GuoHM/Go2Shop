package com.go2shop.authentication.service;

import java.security.Principal;

import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.go2shop.authentication.model.LoginRequestDTO;
import com.go2shop.authentication.model.Oauth2TokenDTO;

public interface UserAuthService {

	Oauth2TokenDTO login(Principal principal, LoginRequestDTO loginRequest) throws HttpRequestMethodNotSupportedException;
}
