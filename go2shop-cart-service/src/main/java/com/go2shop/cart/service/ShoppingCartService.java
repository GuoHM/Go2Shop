package com.go2shop.cart.service;

import java.util.List;
import java.util.Optional;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;

public interface ShoppingCartService {
	
	ShoppingCartDTO createShoppingCart(ShoppingCartDTO shoppingCartDTO);
	
	Optional<ShoppingCartProductDTO> getShoppingCartProduct(Long shoppingCartProductID) throws BusinessException;
	
	List<ShoppingCartProductDTO> getAllShoppingCartProduct(Long shoppingCartID) throws BusinessException;
	
	void deleteAllProduct(Long shoppingCartID);
	
	void deleteShoppingCartProductById(Long shoppingCartProductId);
	
	void deleteShoppingCartProductByProductId(Long productId);
	
	void deleteShoppingCartProductByCartIdAndProductId(Long cartId, Long productId);
	
	ShoppingCartDTO getShoppingCartByUserId(Long userID);
	
	ShoppingCartProductDTO createShoppingCartProduct(ShoppingCartProductDTO shoppingCartProductDTO);
	
	ShoppingCartProductDTO updateQuantity(Long cartId, Long productID, int productQuantity);
	
	Long countCartSizeByDistinctProductIds(Long userId);
}
