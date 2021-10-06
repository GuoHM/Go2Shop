package com.go2shop.cart.service.feign.fallback;

import org.springframework.http.ResponseEntity;

import com.go2shop.cart.service.feign.CatalogueService;
import com.go2shop.model.product.ProductDTO;

public class CatalogueServiceFallback implements CatalogueService {

	@Override
	public ResponseEntity<ProductDTO> getProductById(Long id) {
		return ResponseEntity.badRequest().body(null); 
	}

}
