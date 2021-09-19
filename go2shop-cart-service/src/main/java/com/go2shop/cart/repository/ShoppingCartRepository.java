package com.go2shop.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.go2shop.cart.entity.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
	
	Optional<ShoppingCart> findByUserId(Long userID);
}
