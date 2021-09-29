package com.go2shop.catalogue.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.product.ProductDTO;

public interface CatalogueService {

	List<ProductDTO> getCatalogue();

	Optional<ProductDTO> getProductById(Long id);

	String uploadImage(MultipartFile file) throws BusinessException, IOException;

	ProductDTO createCatalogue(ProductDTO product);
}
