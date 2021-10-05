package com.go2shop.cart.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.go2shop.cart.entity.ShoppingCartProduct;
import com.go2shop.model.cart.ShoppingCartDTO;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.cart.UserToProductDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ShoppingCartProductMapper {

	public ShoppingCartProductDTO toDto(ShoppingCartProduct shoppingCartProduct);
	
	public ShoppingCartProduct toEntity(ShoppingCartProductDTO shoppingCartProductDTO);
	
	@Mapping(target = "quantity", source = "userToProductDTO.quantity")
	@Mapping(target = "productId", source = "userToProductDTO.productId")
	@Mapping(target = "shoppingCartId", source = "shoppingCartDTO.id")
	public ShoppingCartProduct toEntityFromUserToProductAndCart(
			UserToProductDTO userToProductDTO, ShoppingCartDTO shoppingCartDTO);
}
