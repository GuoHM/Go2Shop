package com.go2shop.cart.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		ShoppingCartDTO result = shoppingCartService.createShoppingCart(shoppingCartDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@PostMapping(value = "/shoppingCartProduct/create")
	public ResponseEntity<ShoppingCartProductDTO> createShoppingCartProduct
		(@RequestBody(required = true) ShoppingCartProductDTO shoppingCartProductDTO) {
		ShoppingCartProductDTO result = shoppingCartService.createShoppingCartProduct(shoppingCartProductDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@GetMapping("/shoppingCart/{userID}")
	public ResponseEntity<ShoppingCartDTO> getShoppingCartByUserId(Long userID) {
		Optional<ShoppingCartDTO> result = this.shoppingCartService.getShoppingCartByUserId(userID);
		return result.map(response -> ResponseEntity.ok().body(response))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/shoppingCartProduct/product/{shoppingCartProductID}")
	public ResponseEntity<ShoppingCartProductDTO> getShoppingCartProduct(@PathVariable Long shoppingCartProductID) {
		Optional<ShoppingCartProductDTO> result = this.shoppingCartService.getShoppingCartProduct(shoppingCartProductID);
		return result.map(shoppingCartProduct -> ResponseEntity.ok().body(shoppingCartProduct))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/shoppingCartProduct/shoppingCart/{shoppingCartID}")
	public ResponseEntity<List<ShoppingCartProductDTO>> getAllShoppingCartProduct(Long shoppingCartID) {
		return ResponseEntity.ok().body(this.shoppingCartService.getAllShoppingCartProduct(shoppingCartID));
	}
	
	@PostMapping(value = "/shoppingCartProduct/delete")
	public void deleteAllProduct(Long shoppingCartID) {
		this.shoppingCartService.deleteAllProduct(shoppingCartID);
	}
	
	@PostMapping(value = "/shoppingCartProduct/delete/{shoppingCartProductID}")
	public void deleteShoppingCartProduct(Long shoppingCartProductID) {
		this.shoppingCartService.deleteShoppingCartProduct(shoppingCartProductID);
	}
	
	@PutMapping(value = "/shoppingCartProduct/update/{productID}/{shoppingCartID}/{productQuantity}")
	public ResponseEntity<ShoppingCartProductDTO> updateQuantity(Long productID, int productQuantity, Long shoppingCartID) {
		ShoppingCartProductDTO result = this.shoppingCartService.updateQuantity(productID, productQuantity, shoppingCartID);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
