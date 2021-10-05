package com.go2shop.user.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.user.service.feign.fallback.ShoppingCartServiceFallback;

@FeignClient(value = "cart-service", fallback = ShoppingCartServiceFallback.class)
public interface ShoppingCartService {
	
	@PostMapping("/shoppingCart/cart/create")
	ResponseEntity<ShoppingCartDTO> createCartForUser(@RequestBody(required = true) ShoppingCartDTO cart);

}
