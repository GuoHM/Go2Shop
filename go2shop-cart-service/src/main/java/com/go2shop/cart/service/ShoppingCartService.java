package com.go2shop.cart.service;

import java.util.List;
import java.util.Optional;

import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;

public interface ShoppingCartService {
	
	ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO);
	
	ShoppingCartProductDTO createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO);
	
	Optional<ShoppingCartDTO> getShoppingCartByUserId(Long userID);
	
	Optional<ShoppingCartProductDTO> getShoppingCartProduct(Long shoppingCartProductID);
	
	List<ShoppingCartProductDTO> getAllShoppingCartProduct(Long shoppingCartID);
	
	void deleteAllProduct(Long shoppingCartID);
	
	void deleteShoppingCartProduct(Long shoppingCartProductID);
	
	ShoppingCartProductDTO updateQuantity(Long productID, int productQuantity);
}
