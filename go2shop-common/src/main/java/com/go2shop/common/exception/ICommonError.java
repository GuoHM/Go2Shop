package com.go2shop.common.exception;

import java.io.Serializable;

public interface ICommonError extends Serializable {
	public int getErrCode();

	public String getErrMsg();

	public ICommonError setErrMsg(String errMsg);
}
