package com.go2shop.common.exception;

import org.springframework.http.HttpStatus;

public enum EmBusinessError implements ICommonError {

	// common error start with A101
	PARAMETER_VALIDATION_ERROR("A101", "Invalid parameter", HttpStatus.BAD_REQUEST),
	UNKNOW_ERROR("A102", "Unknow error", HttpStatus.BAD_REQUEST),
	TOKEN_EXPIRE("A103", "Token expire", HttpStatus.UNAUTHORIZED),
	SERVICE_NOT_AVAILABLE("A104", "Service not available for now, please try later", HttpStatus.SERVICE_UNAVAILABLE),
	IO_EXCEPTION("A105", "Upload fail", HttpStatus.BAD_REQUEST),
	TOO_MANY_REQUESTS("A106", "Too many requests. Please try again later.", HttpStatus.TOO_MANY_REQUESTS),

	// auth error start with B101
	USER_NOT_EXIST("B101", "User do not exist", HttpStatus.UNAUTHORIZED),
	USER_LOGIN_FAIL("B102", "User credentials not valid", HttpStatus.UNAUTHORIZED),
	USER_NOT_LOGIN("B103", "User do not login", HttpStatus.UNAUTHORIZED),
	USER_USERNAME_EXIST("B104", "Username already exist", HttpStatus.UNAUTHORIZED),
	
	CATALOGUE_IMAGE_NOT_FOUND("C101", "Image not found", HttpStatus.BAD_REQUEST),
	CART_NOT_EXIST("C102", "Cart not found", HttpStatus.NOT_FOUND),
	PRODUCT_NOT_EXIST("C103", "Product not found", HttpStatus.NOT_FOUND),
	NOT_ENOUGH_PRODUCT_STOCK("C104", "Product's stock is insufficient", HttpStatus.BAD_REQUEST),
	PAYNOW_SERVICE_NOT_AVAILABLE("C105", "PayNow service not available", HttpStatus.SERVICE_UNAVAILABLE);
	
	private String errCode;
	private String errMsg;
	private HttpStatus statusCode;

	private EmBusinessError(String errCode, String errMsg, HttpStatus statusCode) {
		this.errCode = errCode;
		this.errMsg = errMsg;
		this.statusCode = statusCode;
	}

	@Override
	public String getErrCode() {
		return this.errCode;
	}

	@Override
	public String getErrMsg() {
		return this.errMsg;
	}

	@Override
	public ICommonError setErrMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

	@Override
	public HttpStatus getStatusCode() {
		return this.statusCode;
	}
}
