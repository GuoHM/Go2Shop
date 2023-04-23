package com.go2shop.authentication.controller;

import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@RestController
@RequestMapping("/oauth")
public class OauthController extends BaseController {
	
	@Autowired
	private UserAuthService userAuthService;

	@Autowired
	private TokenEndpoint tokenEndpoint;
	private static final Logger logger = LoggerFactory.getLogger(OauthController.class);

	@Autowired
	private HttpServletRequest request;
	private static final int MAX_ATTEMPT = 10;
	private LoadingCache<String, Integer> attemptCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build(new CacheLoader<String, Integer>() {
		@Override
		public Integer load(final String key) {
			return 0;
		}
	});

	@PostMapping(value = "/token")
	public ResponseEntity<UserTokenDTO> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws BusinessException, HttpRequestMethodNotSupportedException {
		String requestIp = getClientIp();
		isBlocked(requestIp);

		OAuth2AccessToken oAuth2AccessToken = null;
		try {
			oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
			resetCache(requestIp);
		} catch (InvalidGrantException ex) {
			throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
		}
		return ResponseEntity.ok().body(userAuthService.handleLoginSuccess(oAuth2AccessToken, parameters.get("username")));
	}

	private void isBlocked(String ip) throws BusinessException {
		try {
			int temp = attemptCache.get(ip);
			if (temp >= MAX_ATTEMPT) {
				logger.error("Too many requests: " + ip);
				throw new BusinessException(EmBusinessError.TOO_MANY_REQUESTS);
			} else {
				attemptCache.put(ip, ++temp);
			}
		} catch (final ExecutionException ex) {
			throw new BusinessException(EmBusinessError.TOO_MANY_REQUESTS);
		}
	}

	private void resetCache(String ip) throws BusinessException {
		try {
			attemptCache.refresh(ip);
		} catch (final Exception ex) {
			throw new BusinessException(EmBusinessError.TOO_MANY_REQUESTS);
		}
	}

	private String getClientIp() {
		String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(request.getRemoteAddr())) {
			return request.getRemoteAddr();
		}
		return xfHeader.split(",")[0];
	}
}
