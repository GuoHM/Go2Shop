package com.go2shop.common.exception;

public class BussinessException extends Exception implements ICommonError {

	private static final long serialVersionUID = 5455416838935383604L;
	// emun
	private final ICommonError commonError;

	// Accept commonError to construct exception
	public BussinessException(ICommonError commonError) {
		super();
		this.commonError = commonError;
	}

	// override error message to construct exception
	public BussinessException(ICommonError commonError, String errMsg) {
		super();
		this.commonError = commonError;
		this.commonError.setErrMsg(errMsg);
	}

	@Override
	public int getErrCode() {
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

}