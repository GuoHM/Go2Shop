package com.go2shop.catalogue.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.go2shop.catalogue.entity.ProductReview;
import com.go2shop.model.product.ProductReviewDTO;

@Mapper(componentModel = "spring", uses = {})
public interface ProductReviewMapper {

	@Mapping(target = "product", ignore = true)
	public ProductReviewDTO toDto(ProductReview productReview);
	
	public ProductReview toEntity(ProductReviewDTO productReview);
}
