package com.go2shop.gateway.authorization;

import net.minidev.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;

import com.go2shop.gateway.config.AuthoritiesConfig;

import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
	
	@Autowired
	private AuthoritiesConfig authoritiesConfig;

	@Override
	public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext object) {
		return authentication.map(auth -> new AuthorizationDecision(checkAuthorities(object.getExchange(), auth)));
	}

	private boolean checkAuthorities(ServerWebExchange exchange, Authentication authentication) {
		Set<String> auths = new HashSet<>();
		AntPathMatcher pathMatcher = new AntPathMatcher();
		String path = exchange.getRequest().getURI().getPath();
		getAuthoritiesConfigs().forEach((k, v) -> {
			String pattern = k;
			if (pathMatcher.match(pattern, path)) {
				auths.addAll(v);
			}
		});
		boolean granted = false;
		List<GrantedAuthority> authorities = new ArrayList<>(authentication.getAuthorities());
		if (!authorities.isEmpty()) {
			granted = authorities.stream().anyMatch(a -> auths.contains(a.getAuthority()));
		}
		return granted;
	}
	
	private Map<String, List<String>> getAuthoritiesConfigs() {
		Map<String, List<String>> result = new HashMap<>();
		authoritiesConfig.getAuthroities().forEach(authorityConfig -> {
			authorityConfig.getUrls().forEach(url -> {
				List<String> roles = authorityConfig.getRoles().stream().map(role -> role = "ROLE_" + role).collect(Collectors.toList());
				result.put(url, roles);
			});
		});
		return result;
	}
}