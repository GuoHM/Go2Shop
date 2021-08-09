package com.go2shop.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.go2shop.gateway.authorization.AuthorizationManager;
import com.go2shop.gateway.component.RestAuthenticationEntryPoint;
import com.go2shop.gateway.component.RestfulAccessDeniedHandler;
import com.go2shop.gateway.filter.IgnoreUrlsRemoveJwtFilter;

import cn.hutool.core.util.ArrayUtil;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
	@Autowired
	AuthorizationManager authorizationManager;
	@Autowired
	private IgnoreUrlsConfig ignoreUrlsConfig;
	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	@Autowired
	private IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;

	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter());
		http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
		http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
		http.authorizeExchange().pathMatchers(ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class)).permitAll()
				.anyExchange().access(authorizationManager)
				.and().exceptionHandling().accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthenticationEntryPoint)
				.and().csrf().disable();
		return http.build();
	}

	@Bean
	public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
		JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
	}

}