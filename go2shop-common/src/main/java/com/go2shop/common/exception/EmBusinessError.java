package com.go2shop.common.exception;

public enum EmBusinessError implements ICommonError {

	//common error start with 10000
    PARAMETER_VALIDATION_ERROR(10001, "Invalid parameter"),
    UNKNOW_ERROR(10002, "Unknow error"),
    TOKEN_EXPIRE(10003, "Token expire"),
	
	//auth error start with 20000
    USER_NOT_EXIST(20001, "User do not exist"),
    USER_LOGIN_FAIL(20002, "Username password didn't match"),
    USER_NOT_LOGIN(20003, "User do not login"),
    USER_USERNAME_EXIST(20004, "Username already exist"),
    USER_ALREADY_CHECKED(20005, "User already vertified Email address"), 
    USER_USERNAME_EMIAL_NOT_MATCH(20006, "Username and email didn't match");
    
	private int errCode;
	private String errMsg;

	private EmBusinessError(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

	@Override
	public int getErrCode() {
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
}
