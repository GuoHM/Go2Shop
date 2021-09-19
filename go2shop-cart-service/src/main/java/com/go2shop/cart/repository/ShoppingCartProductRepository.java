package com.go2shop.cart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.go2shop.cart.entity.ShoppingCartProduct;

public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, Long> {

	List<ShoppingCartProduct> findByShoppingCartId(Long shoppingCartID);
	
	void deleteByProductId(Long productID);
	
	void deleteByShoppingCartId(Long shoppingCartID);
	
	Optional<ShoppingCartProduct> findByProductId(Long productID);
}
