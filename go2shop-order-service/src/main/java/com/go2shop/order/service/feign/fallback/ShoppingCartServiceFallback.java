package com.go2shop.order.service.feign.fallback;

import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.go2shop.order.service.feign.ShoppingCartService;

public class ShoppingCartServiceFallback implements ShoppingCartService {

	@Override
	public ResponseEntity<Void> deleteShoppingCartProductById(
			@PathVariable("shoppingCartProductID") @NotNull Long shoppingCartProductID) {
		return ResponseEntity.badRequest().build(); 
	}

}
