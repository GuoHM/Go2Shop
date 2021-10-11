package com.go2shop.cart.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.go2shop.cart.entity.ShoppingCartProduct;
import com.go2shop.model.cart.ShoppingCartProductDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ShoppingCartProductMapper {

	@Mapping(target = "product", ignore = true)
	public ShoppingCartProductDTO toDto(ShoppingCartProduct shoppingCartProduct);
	
	@Mapping(target = "productId", source = "product.id")
	public ShoppingCartProduct toEntity(ShoppingCartProductDTO shoppingCartProductDTO);
	
	public List<ShoppingCartProductDTO> toDto(List<ShoppingCartProduct> shoppingCartProducts);
}
