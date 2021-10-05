package com.go2shop.catalogue.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.go2shop.catalogue.entity.ProductImage;

public interface ProductImageRepository
		extends JpaRepository<ProductImage, Long>, JpaSpecificationExecutor<ProductImage> {

}
