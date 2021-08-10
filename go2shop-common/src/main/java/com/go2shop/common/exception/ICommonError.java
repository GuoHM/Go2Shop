package com.go2shop.common.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

public interface ICommonError extends Serializable {
	public String getErrCode();

	public String getErrMsg();

	public HttpStatus getStatusCode();

	public ICommonError setErrMsg(String errMsg);
}
