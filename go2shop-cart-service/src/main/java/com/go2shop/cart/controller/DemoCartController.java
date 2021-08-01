package com.go2shop.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.cart.service.DemoCartService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;

@RestController
@RequestMapping(value = "/cart/demo")
@RefreshScope
public class DemoCartController extends BaseController {
	
	@Autowired
	DemoCartService cartService;
	
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
}
