package com.go2shop.cart.service;

import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;

public interface ShoppingCartService {
	
	ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO);
	
	ShoppingCartProductDTO createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO);
	
	ShoppingCartDTO getShoppingCart(Long shoppingCartID);
	
	ShoppingCartProductDTO getShoppingCartProduct(Long shoppingCartProductID);
	
	void deleteAllProduct(Long shoppingCartID);
	
	void deleteShoppingCartProduct(Long shoppingCartProductID);
	
	ShoppingCartProductDTO updateQuantity(Long productID, int productQuantity);
}
