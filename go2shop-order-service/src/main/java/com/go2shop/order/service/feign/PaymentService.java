package com.go2shop.order.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.order.service.feign.fallback.PaymentServiceFallback;

@FeignClient(value = "payment-service", fallback = PaymentServiceFallback.class)
public interface PaymentService {
	
	@GetMapping("/payment/payByPayNow")
	public ResponseEntity<Void> payByPayNow() throws BusinessException;
	
	@GetMapping("/payment/payByCard")
	public ResponseEntity<Void> payByCard();

}
