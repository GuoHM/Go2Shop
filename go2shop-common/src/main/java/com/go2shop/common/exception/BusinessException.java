package com.go2shop.common.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends Exception implements ICommonError {

	private static final long serialVersionUID = 5455416838935383604L;
	// emun
	private final ICommonError commonError;

	// Accept commonError to construct exception
	public BusinessException(ICommonError commonError) {
		super();
		this.commonError = commonError;
	}

	// override error message to construct exception
	public BusinessException(ICommonError commonError, String errMsg) {
		super();
		this.commonError = commonError;
		this.commonError.setErrMsg(errMsg);
	}

	@Override
	public String getErrCode() {
		return this.commonError.getErrCode();
	}

	@Override
	public String getErrMsg() {
		return this.commonError.getErrMsg();
	}

	@Override
	public ICommonError setErrMsg(String str) {
		this.commonError.setErrMsg(str);
		return this;
	}

	@Override
	public HttpStatus getStatusCode() {
		return this.commonError.getStatusCode();
	}

}