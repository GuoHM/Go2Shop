package com.go2shop.authentication.model;

public class UserTokenDTO {

	private String token;

	private String refreshToken;

	private String tokenHead;

	private int expiresIn;

	private Long userId;

	private Long cartId;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTokenHead() {
		return tokenHead;
	}

	public void setTokenHead(String tokenHead) {
		this.tokenHead = tokenHead;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

}
