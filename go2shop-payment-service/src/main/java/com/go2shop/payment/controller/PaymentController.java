package com.go2shop.payment.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;

@RestController
@RequestMapping(value = "/payment")
@RefreshScope
public class PaymentController extends BaseController {
	
	@GetMapping("/payByPayNow")
	public ResponseEntity<Void> payByPayNow() throws BusinessException {
		throw new BusinessException(EmBusinessError.PAYNOW_SERVICE_NOT_AVAILABLE);
	}
	
	@GetMapping("/payByCard")
	public ResponseEntity<Void> payByCard() {
		return ResponseEntity.ok().build();
	}
}
