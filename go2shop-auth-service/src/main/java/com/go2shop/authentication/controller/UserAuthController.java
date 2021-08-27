package com.go2shop.authentication.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.authentication.model.UserTokenDTO;
import com.go2shop.authentication.service.UserAuthService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;

@RestController
@RequestMapping("/oauth")
public class UserAuthController extends BaseController {
	
	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private TokenEndpoint tokenEndpoint;

	@PostMapping(value = "/token")
	public ResponseEntity<UserTokenDTO> postAccessToken(Principal principal,
			@RequestParam Map<String, String> parameters) throws BusinessException, HttpRequestMethodNotSupportedException {
		OAuth2AccessToken oAuth2AccessToken = null;
		try {
			oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
		} catch (InvalidGrantException ex) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		return ResponseEntity.ok().body(userAuthService.handleLoginSuccess(oAuth2AccessToken));
	}
}
