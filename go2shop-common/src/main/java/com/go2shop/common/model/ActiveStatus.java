package com.go2shop.common.model;

public enum ActiveStatus {

	ACTIVE("A"), INACTIVE("I");

	private final String code;

	private ActiveStatus(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
	public static ActiveStatus getByCode(String code) {
		code = code.toUpperCase();
		switch (code) {
		case "A":
			return ActiveStatus.ACTIVE;
		case "I":
			return ActiveStatus.INACTIVE;
		default:
			return ActiveStatus.INACTIVE;
		}
	}

}
