package com.go2shop.order.service.feign.fallback;

import org.springframework.http.ResponseEntity;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.order.service.feign.PaymentService;

public class PaymentServiceFallback implements PaymentService {
	
	@Override
	public ResponseEntity<Void> payByPayNow() throws BusinessException {
		return ResponseEntity.badRequest().build(); 
	};
	
	@Override
	public ResponseEntity<Void> payByCard() {
		return ResponseEntity.badRequest().build(); 
	};

}
