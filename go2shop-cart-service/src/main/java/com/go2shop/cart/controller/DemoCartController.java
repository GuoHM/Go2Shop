package com.go2shop.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.cart.service.DemoCatalogueService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BussinessException;

@RestController
@RequestMapping(value = "/cart/demo")
@RefreshScope
public class DemoCartController {
	
	@Autowired
	DemoCatalogueService demoCatalogueService;
	
	@Value("${server.port}")
	private int serverPort;

	@GetMapping("/cartId/{cartId}")
	public String getCart(@PathVariable("cartId") String cartId) {
		return "This is form cart service with cartId: " + cartId + ", deployed port: " + serverPort;
	}

	@GetMapping("/cartId/{cartId}/catalogueId/{catalogueId}")
	public String getCartWithCatalogue(@PathVariable("cartId") String cartId, @PathVariable("catalogueId") String catalogueId) throws BussinessException {
		return "Get from cart service with cart id " + cartId + ", \n" + demoCatalogueService.getCatalogue(catalogueId);
	}
}
