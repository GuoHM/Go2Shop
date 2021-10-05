package com.go2shop.cart.service;

import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.cart.UserToProductDTO;

public interface ShoppingCartService {
	
	ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO);
	
//	ShoppingCartProductDTO createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO);
	
	ShoppingCartDTO getShoppingCart(Long shoppingCartID);
	
	ShoppingCartProductDTO getShoppingCartProduct(Long shoppingCartProductID);
	
	void deleteAllProduct(Long shoppingCartID);
//	
//	void deleteShoppingCartProduct(Long shoppingCartProductID);
	
//	ShoppingCartProductDTO updateQuantity(Long productID, int productQuantity);
	
	ShoppingCartDTO getShoppingCartByUserId(Long userID);
	
	ShoppingCartProductDTO createOrUpdateShoppingCartProduct(UserToProductDTO userToProductDTO);
	
	ShoppingCartProductDTO updateQuantity(Long cartId, Long productID, int productQuantity);
	
	void deleteShoppingCartProduct(Long cartId, Long shoppingCartProductID);
	
	Long countCartSizeByDistinctProductIds(Long userId);
}
