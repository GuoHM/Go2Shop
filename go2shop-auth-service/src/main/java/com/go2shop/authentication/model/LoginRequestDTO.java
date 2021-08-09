package com.go2shop.authentication.model;

import javax.validation.constraints.NotBlank;

public class LoginRequestDTO {

	@NotBlank(message = "user name cannot be null")
	private String username;

	@NotBlank(message = "password cannot be null")
	private String password;

	private String grantType = "password";

	private String clientId = "client-app";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
