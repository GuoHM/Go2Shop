package com.go2shop.cart.service.feign.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.go2shop.cart.service.feign.CatalogueService;
import com.go2shop.model.product.ProductDTO;

@Component
public class CatalogueServiceFallback implements CatalogueService {

	@Override
	public ResponseEntity<ProductDTO> getProductById(Long id) {
		return ResponseEntity.badRequest().body(null); 
	}

}
