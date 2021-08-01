package com.go2shop.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.common.exception.BussinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.netflix.client.ClientException;

@RestController
public class BaseController {

	private static final String ERROR_CODE = "errCode";
	private static final String ERROR_MSG = "errMsg";

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public ResponseEntity<Object> handlerException(HttpServletRequest request, Exception ex) throws Exception {

		Map<String, Object> responseData = new HashMap<>();

		if (ex instanceof BussinessException) {
			BussinessException bussinessException = (BussinessException) ex;
			responseData.put(ERROR_CODE, bussinessException.getErrCode());
			responseData.put(ERROR_MSG, bussinessException.getErrMsg());
		} else if (ex.getCause() instanceof ClientException) {
			throw(ex);
		}
//		else if (ex instanceof AuthenticationException) {
//			responseData.put(ERROR_CODE, EmBusinessError.USER_LOGIN_FAIL.getErrCode());
//			responseData.put(ERROR_MSG, EmBusinessError.USER_LOGIN_FAIL.getErrMsg());
//		} 
		else {
			responseData.put(ERROR_CODE, EmBusinessError.UNKNOW_ERROR.getErrCode());
			responseData.put(ERROR_MSG, EmBusinessError.UNKNOW_ERROR.getErrMsg());
		}
		return ResponseEntity.ok().body(responseData);

	}

}