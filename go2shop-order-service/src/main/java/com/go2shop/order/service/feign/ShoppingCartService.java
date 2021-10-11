package com.go2shop.order.service.feign;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.go2shop.order.service.feign.fallback.ShoppingCartServiceFallback;

@FeignClient(value = "cart-service", fallback = ShoppingCartServiceFallback.class)
public interface ShoppingCartService {
	
	@DeleteMapping(value = "/shoppingCart/shoppingCartProduct/{shoppingCartProductID}")
	public ResponseEntity<Void> deleteShoppingCartProductById(
			@PathVariable("shoppingCartProductID") @NotNull Long shoppingCartProductID);

}
