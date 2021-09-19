package com.go2shop.cart.service.mapper;

import org.mapstruct.Mapper;

import com.go2shop.cart.entity.ShoppingCart;
import com.go2shop.model.cart.ShoppingCartDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ShoppingCartMapper {

	public ShoppingCartDTO toDto(ShoppingCart shoppingCart);
	
	public ShoppingCart toEntity(ShoppingCartDTO shoppingCartDTO);
}
