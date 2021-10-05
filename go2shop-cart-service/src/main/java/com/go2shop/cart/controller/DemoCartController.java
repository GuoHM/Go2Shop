package com.go2shop.cart.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.cart.service.DemoCartService;
import com.go2shop.cart.service.ShoppingCartService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.cart.UserToProductDTO;

@RestController
@RequestMapping(value = "/cart/demo")
@RefreshScope
public class DemoCartController extends BaseController {
	
	@Autowired
	DemoCartService cartService;
	
	@Autowired
	ShoppingCartService shoppingCartService;
	
	@Value("${server.port}")
	private int serverPort;

	@GetMapping("/cartId/{cartId}")
	public String getCart(@PathVariable("cartId") String cartId) throws BusinessException {
		return cartService.getCartById(cartId);
	}

	@GetMapping("/cartId/{cartId}/catalogueId/{catalogueId}")
	public String getCartWithCatalogue(@PathVariable("cartId") String cartId, @PathVariable("catalogueId") String catalogueId) throws BusinessException {
		return cartService.getCartWithCatalogue(cartId, catalogueId);
	}
	
	@PostMapping("/cart/create")
	public ResponseEntity<ShoppingCartDTO> createCartForUser(@RequestBody(required = true) ShoppingCartDTO cart) {
		return ResponseEntity.ok().body(shoppingCartService.createShoppingCart(cart));
	}
	
	@PostMapping("/addProductToCart")
	public ResponseEntity<ShoppingCartProductDTO> addProductToCartForUser(@RequestBody(required = true) UserToProductDTO cart) {
		return ResponseEntity.ok().body(shoppingCartService.createOrUpdateShoppingCartProduct(cart));
	}
	
	@GetMapping("/cart/size/{userId}")
	public ResponseEntity<Long> getCartWithCatalogue(@PathVariable("userId") @NotNull Long userId) {
		return ResponseEntity.ok().body(shoppingCartService.countCartSizeByDistinctProductIds(userId));
	}
}
