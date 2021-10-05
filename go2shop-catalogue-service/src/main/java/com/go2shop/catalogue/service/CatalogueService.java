package com.go2shop.catalogue.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.go2shop.catalogue.entity.ProductSearchDTO;
import org.springframework.web.multipart.MultipartFile;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.product.ProductDTO;

public interface CatalogueService {

	List<ProductDTO> getCatalogue();

	Optional<ProductDTO> getProductById(Long id);

	Page<ProductDTO> searchProducts(ProductSearchDTO searchDTO, Pageable page);

	String uploadImage(MultipartFile file) throws BusinessException, IOException;

	ProductDTO createCatalogue(ProductDTO product);
}
