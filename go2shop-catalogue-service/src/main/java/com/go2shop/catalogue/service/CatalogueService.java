package com.go2shop.catalogue.service;

import java.util.List;
import java.util.Optional;

import com.go2shop.model.product.ProductDTO;

public interface CatalogueService {
	
	List<ProductDTO> getCatalogue();
	Optional<ProductDTO> getProductById(Long id);
}
