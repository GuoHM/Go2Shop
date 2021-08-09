package com.go2shop.gateway.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "secure")
public class AuthoritiesConfig {

	private List<AuthorityConfig> authroities;

	public List<AuthorityConfig> getAuthroities() {
		return authroities;
	}

	public void setAuthroities(List<AuthorityConfig> authroities) {
		this.authroities = authroities;
	}

	public static class AuthorityConfig {
		private List<String> urls;
		private List<String> roles;

		public List<String> getUrls() {
			return urls;
		}

		public void setUrls(List<String> urls) {
			this.urls = urls;
		}

		public List<String> getRoles() {
			return roles;
		}

		public void setRoles(List<String> roles) {
			this.roles = roles;
		}

	}

}
