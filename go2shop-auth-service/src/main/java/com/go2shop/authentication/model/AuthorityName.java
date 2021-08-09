package com.go2shop.authentication.model;

public enum AuthorityName {
	BUYER, SELLER;
	public static AuthorityName getByString(String name) {
		name = name.toUpperCase();
		switch (name) {
		case "BUYER":
			return AuthorityName.BUYER;
		case "SELLER":
			return AuthorityName.SELLER;
		default:
			return null;
		}
	}
}
