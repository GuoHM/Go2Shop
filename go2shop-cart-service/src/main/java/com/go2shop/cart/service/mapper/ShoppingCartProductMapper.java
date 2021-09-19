package com.go2shop.cart.service.mapper;

import org.mapstruct.Mapper;

import com.go2shop.cart.entity.ShoppingCartProduct;
import com.go2shop.model.cart.ShoppingCartProductDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ShoppingCartProductMapper {

	public ShoppingCartProductDTO toDto(ShoppingCartProduct shoppingCartProduct);
	
	public ShoppingCartProduct toEntity(ShoppingCartProductDTO shoppingCartProductDTO);
}
