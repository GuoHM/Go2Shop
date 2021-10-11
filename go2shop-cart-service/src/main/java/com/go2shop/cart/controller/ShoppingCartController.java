package com.go2shop.cart.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.cart.service.ShoppingCartService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;
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
	
	@PostMapping("/cart/create")
	public ResponseEntity<ShoppingCartDTO> createCartForUser(@RequestBody(required = true) ShoppingCartDTO cart) {
		return ResponseEntity.ok().body(shoppingCartService.createShoppingCart(cart));
	}
	
	@GetMapping("/cart/size/{userId}")
	public ResponseEntity<Long> getCartWithCatalogue(@PathVariable("userId") @NotNull Long userId) {
		return ResponseEntity.ok().body(shoppingCartService.countCartSizeByDistinctProductIds(userId));
	}
	
	@PostMapping(value = "/shoppingCartProduct/create")
	public ResponseEntity<ShoppingCartProductDTO> createShoppingCartProduct
		(@RequestBody(required = true) ShoppingCartProductDTO shoppingCartProductDTO) {
		ShoppingCartProductDTO result = shoppingCartService.createShoppingCartProduct(shoppingCartProductDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
	
	@GetMapping("/shoppingCart/{userID}")
	public ResponseEntity<ShoppingCartDTO> getShoppingCartByUserId(@PathVariable("userID") @NotNull Long userID) {
		ShoppingCartDTO result = this.shoppingCartService.getShoppingCartByUserId(userID);
		if(result != null) {
			return ResponseEntity.ok().body(result);
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/shoppingCartProduct/product/{shoppingCartProductID}")
	public ResponseEntity<ShoppingCartProductDTO> getShoppingCartProduct(@PathVariable Long shoppingCartProductID) throws BusinessException {
		Optional<ShoppingCartProductDTO> result = this.shoppingCartService.getShoppingCartProduct(shoppingCartProductID);
		return result.map(shoppingCartProduct -> ResponseEntity.ok().body(shoppingCartProduct))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/shoppingCartProduct/shoppingCart/{shoppingCartID}")
	public ResponseEntity<List<ShoppingCartProductDTO>> getAllShoppingCartProduct(@PathVariable("shoppingCartID") Long shoppingCartID) throws BusinessException {
		return ResponseEntity.ok().body(this.shoppingCartService.getAllShoppingCartProduct(shoppingCartID));
	}
	
	@DeleteMapping(value = "/shoppingCartProduct/delete/{shoppingCartID}")
	public ResponseEntity<Void> deleteAllProduct(@PathVariable("shoppingCartID") @NotNull Long shoppingCartID) {
		this.shoppingCartService.deleteAllProduct(shoppingCartID);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/shoppingCartProduct/delete/{shoppingCartID}/{productID}")
	public ResponseEntity<Void> deleteShoppingCartProduct(
			@PathVariable("shoppingCartID") @NotNull Long shoppingCartID, 
			@PathVariable("productID") @NotNull Long productID) {
		this.shoppingCartService.deleteShoppingCartProductByCartIdAndProductId(shoppingCartID, productID);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping(value = "/shoppingCartProduct/{shoppingCartProductID}")
	public ResponseEntity<Void> deleteShoppingCartProductById(
			@PathVariable("shoppingCartProductID") @NotNull Long shoppingCartProductID) {
		this.shoppingCartService.deleteShoppingCartProductById(shoppingCartProductID);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping(value = "/shoppingCartProduct/update/{productID}/{shoppingCartID}/{productQuantity}")
	public ResponseEntity<ShoppingCartProductDTO> updateQuantity(Long productID, int productQuantity, Long shoppingCartID) {
		ShoppingCartProductDTO result = this.shoppingCartService.updateQuantity(shoppingCartID, productID, productQuantity);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
