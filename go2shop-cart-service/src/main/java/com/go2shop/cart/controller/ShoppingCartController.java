package com.go2shop.cart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.cart.service.ShoppingCartService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;

@RestController
@RequestMapping(value = "/shoppingCart")
@RefreshScope
public class ShoppingCartController extends BaseController {
	
	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping(value = "/shoppingCart/create")
	public ResponseEntity<ShoppingCartDTO> createShoppingCart
		(@RequestBody(required = true) ShoppingCartDTO shoppingCartDTO)	{
		return ResponseEntity.ok().body(shoppingCartService.createShoppingCart(shoppingCartDTO));
	}
	
	@PostMapping(value = "/shoppingCartProduct/create")
	public ResponseEntity<ShoppingCartProductDTO> createShoppingCartProduct
		(@RequestBody(required = true) ShoppingCartProductDTO shoppingCartProductDTO) {
		return ResponseEntity.ok().body(shoppingCartService.createShoppingCartProduct(shoppingCartProductDTO));
	}
	
	@GetMapping("/shoppingCart/{userID}")
	public ResponseEntity<ShoppingCartDTO> getShoppingCartByUserId(Long userID) {
		return this.shoppingCartService.getShoppingCartByUserId(userID)
				.map(shoppingCart -> ResponseEntity.ok().body(shoppingCart))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/shoppingCartProduct/product/{shoppingCartProductID}")
	public ResponseEntity<ShoppingCartProductDTO> getShoppingCartProduct(@PathVariable Long shoppingCartProductID) {
		return this.shoppingCartService.getShoppingCartProduct(shoppingCartProductID)
				.map(shoppingCartProduct -> ResponseEntity.ok().body(shoppingCartProduct))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/shoppingCartProduct/shoppingCart/{shoppingCartID}")
	public ResponseEntity<List<ShoppingCartProductDTO>> getAllShoppingCartProduct(Long shoppingCartID) {
		return ResponseEntity.ok().body(this.shoppingCartService.getAllShoppingCartProduct(shoppingCartID));
	}
	
	@PostMapping(value = "/shoppingCartProduct/delete")
	public void deleteAllProduct(Long shoppingCartID) {
		shoppingCartService.deleteAllProduct(shoppingCartID);
	}
	
	@PostMapping(value = "/shoppingCartProduct/delete/{shoppingCartProductID}")
	public void deleteShoppingCartProduct(Long shoppingCartProductID) {
		shoppingCartService.deleteShoppingCartProduct(shoppingCartProductID);
	}
	
	@PostMapping(value = "/shoppingCartProduct/update/{productID}")
	public ResponseEntity<ShoppingCartProductDTO> updateQuantity(Long productID, int productQuantity) {
		return ResponseEntity.ok().body(shoppingCartService.updateQuantity(productID, productQuantity));
	}
}
