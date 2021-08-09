package com.go2shop.authentication.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class AuthConfig {
	private Cors cors = new Cors();

	private Jwt jwt = new Jwt();

	public Cors getCors() {
		return cors;
	}

	public void setCors(Cors cors) {
		this.cors = cors;
	}

	public Jwt getJwt() {
		return jwt;
	}

	public void setJwt(Jwt jwt) {
		this.jwt = jwt;
	}

	public static class Cors {
		private List<String> allowedOrigins = new ArrayList<>();

		private List<String> allowedMethods = new ArrayList<>();

		private List<String> allowedHeaders = new ArrayList<>();

		public List<String> getAllowedOrigins() {
			return allowedOrigins;
		}

		public void setAllowedOrigins(List<String> allowedOrigins) {
			this.allowedOrigins = allowedOrigins;
		}

		public List<String> getAllowedMethods() {
			return allowedMethods;
		}

		public void setAllowedMethods(List<String> allowedMethods) {
			this.allowedMethods = allowedMethods;
		}

		public List<String> getAllowedHeaders() {
			return allowedHeaders;
		}

		public void setAllowedHeaders(List<String> allowedHeaders) {
			this.allowedHeaders = allowedHeaders;
		}

	}

	public static class Jwt {
		private String header;

		private String secret;

		private Long expiration;

		private String issuer;

		private String authenticationPath;

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public String getSecret() {
			return secret;
		}

		public void setSecret(String secret) {
			this.secret = secret;
		}

		public Long getExpiration() {
			return expiration;
		}

		public void setExpiration(Long expiration) {
			this.expiration = expiration;
		}

		public String getIssuer() {
			return issuer;
		}

		public void setIssuer(String issuer) {
			this.issuer = issuer;
		}

		public String getAuthenticationPath() {
			return authenticationPath;
		}

		public void setAuthenticationPath(String authenticationPath) {
			this.authenticationPath = authenticationPath;
		}

	}
}
