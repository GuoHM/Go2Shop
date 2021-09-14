package com.go2shop.catalogue.service.mapper;

import org.mapstruct.Mapper;

import com.go2shop.catalogue.entity.ProductImage;
import com.go2shop.model.product.ProductImageDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ProductImageMapper {

	public ProductImageDTO toDto(ProductImage productImage);
	
	public ProductImage toEntity(ProductImageDTO productImage);
}
