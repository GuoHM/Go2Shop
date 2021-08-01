package com.go2shop.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.common.exception.BusinessException;

@RestController
public class BaseController {

	private static final String ERROR_CODE = "errCode";
	private static final String ERROR_MSG = "errMsg";

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseEntity<Object> handlerException(HttpServletRequest request, Exception ex) {
		Map<String, Object> responseData = new HashMap<>();
		BusinessException bussinessException = (BusinessException) ex;
		responseData.put(ERROR_CODE, bussinessException.getErrCode());
		responseData.put(ERROR_MSG, bussinessException.getErrMsg());
		return ResponseEntity.badRequest().body(responseData);
	}

}