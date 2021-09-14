package com.go2shop.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.go2shop.cart.entity.ShoppingCart;


/** 
 * 
 * <Write a short description on the purpose of the class> 
 * 
 * @author P1326154 
 * Created Date Aug 10, 2021 2:26:35 PM 
 * 
*/
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
