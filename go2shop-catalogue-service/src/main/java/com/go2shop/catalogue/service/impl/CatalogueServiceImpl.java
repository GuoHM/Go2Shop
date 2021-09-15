package com.go2shop.catalogue.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.go2shop.catalogue.repository.CatalogueRepository;
import com.go2shop.catalogue.service.CatalogueService;
import com.go2shop.catalogue.service.mapper.ProductMapper;
import com.go2shop.model.product.ProductDTO;

@Service
public class CatalogueServiceImpl implements CatalogueService {
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private CatalogueRepository catalogueRepository;
	
	@Override
	public List<ProductDTO> getCatalogue() {
		return this.catalogueRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
	}
	
	@Override
	public Optional<ProductDTO> getProductById(Long id) {
		return this.catalogueRepository.findById(id).map(productMapper::toDto);
	}
}
