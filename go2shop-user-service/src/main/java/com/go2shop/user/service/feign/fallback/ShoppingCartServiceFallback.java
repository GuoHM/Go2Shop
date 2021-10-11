package com.go2shop.user.service.feign.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.user.service.feign.ShoppingCartService;

@Component
public class ShoppingCartServiceFallback implements ShoppingCartService {

	@Override
	public ResponseEntity<ShoppingCartDTO> createCartForUser(ShoppingCartDTO user) {
		return ResponseEntity.badRequest().body(null); 
	}

}
