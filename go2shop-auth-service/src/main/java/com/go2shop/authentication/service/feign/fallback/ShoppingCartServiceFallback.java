package com.go2shop.authentication.service.feign.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.go2shop.authentication.service.feign.ShoppingCartService;
import com.go2shop.model.cart.ShoppingCartDTO;

@Component
public class ShoppingCartServiceFallback implements ShoppingCartService {

	@Override
	public ResponseEntity<ShoppingCartDTO> getShoppingCartByUserId(Long userID) {
		return ResponseEntity.badRequest().body(null); 
	}

}
