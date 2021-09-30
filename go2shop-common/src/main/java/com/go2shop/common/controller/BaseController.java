package com.go2shop.common.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.util.ReturnExceptionUtils;

@RestController
public class BaseController {

	@ExceptionHandler(BusinessException.class)
	@ResponseBody
	public ResponseEntity<Object> handlerBusinessException(HttpServletRequest request, BusinessException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(ReturnExceptionUtils.build(ex));
	}
	
	@ExceptionHandler(IOException.class)
	@ResponseBody
	public ResponseEntity<Object> handlerIOException(HttpServletRequest request, IOException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ReturnExceptionUtils.buildFromIOExcrption(ex));
	}

}