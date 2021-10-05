package com.go2shop.authentication.service.feign.fallback;

import org.springframework.http.ResponseEntity;

import com.go2shop.authentication.service.feign.ShoppingCartService;
import com.go2shop.model.cart.ShoppingCartDTO;

public class ShoppingCartServiceFallback implements ShoppingCartService {

	@Override
	public ResponseEntity<ShoppingCartDTO> getShoppingCartByUserId(Long userID) {
		return ResponseEntity.badRequest().body(null); 
	}

}
