package com.go2shop.authentication.service.impl;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import com.go2shop.authentication.model.LoginRequestDTO;
import com.go2shop.authentication.model.Oauth2TokenDTO;
import com.go2shop.authentication.service.UserAuthService;

@Service
public class UserAuthServiceImpl implements UserAuthService {

	@Autowired
	private TokenEndpoint tokenEndpoint;

	@Override
	public Oauth2TokenDTO login(Principal principal, LoginRequestDTO loginRequest) throws HttpRequestMethodNotSupportedException {
		Map<String, String> parameters = new HashMap<>(8);
		parameters.put("client_id",
				!StringUtils.isEmpty(loginRequest.getClientId()) ? loginRequest.getClientId() : "client-app");
		parameters.put("grant_type",
				!StringUtils.isEmpty(loginRequest.getGrantType()) ? loginRequest.getGrantType() : "password");
		parameters.put("username", loginRequest.getUsername());
		parameters.put("password", loginRequest.getPassword());
		OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
		Oauth2TokenDTO oauth2TokenDTO = new Oauth2TokenDTO();
		oauth2TokenDTO.setToken(oAuth2AccessToken.getValue());
		oauth2TokenDTO.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
		oauth2TokenDTO.setTokenHead("Bearer ");
		return oauth2TokenDTO;
	}

}
