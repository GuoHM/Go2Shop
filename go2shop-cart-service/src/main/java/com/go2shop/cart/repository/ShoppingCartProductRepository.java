package com.go2shop.cart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.go2shop.cart.entity.ShoppingCartProduct;

public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, Long> {

	List<ShoppingCartProduct> findByShoppingCartId(Long shoppingCartID);
	
	void deleteByProductId(Long productID);
	
	void deleteByShoppingCartId(Long shoppingCartID);
	
	Optional<ShoppingCartProduct> findByProductId(Long productID);
	
	Optional<ShoppingCartProduct> findByShoppingCartIdAndProductId(Long shoppingCartId, Long productId);
	
	void deleteByShoppingCartIdAndProductId(Long cartId, Long productID);
	
	@Query(value = "select count(distinct p.productId) "
			+ "FROM ShoppingCartProduct p where p.shoppingCartId = :cartId")
	Long countSizeByDistinctProductIds(@Param("cartId") Long cartId);
}
