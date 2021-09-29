package com.go2shop.catalogue.service.impl;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.go2shop.catalogue.entity.Product;
import com.go2shop.catalogue.repository.CatalogueRepository;
import com.go2shop.catalogue.service.CatalogueService;
import com.go2shop.catalogue.service.mapper.ProductMapper;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.model.product.ProductDTO;

@Service
public class CatalogueServiceImpl implements CatalogueService {

	@Value("${img.upload-path}")
	private String uploadPath;

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

	@Override
	public String uploadImage(MultipartFile file) throws BusinessException, IOException {
		if (file == null) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		Path path = Paths.get(uploadPath);
		if (Files.notExists(path)) {
			Files.createFile(Files.createDirectories(path));
		}
		String filePath = uploadPath + UUID.randomUUID() + file.getOriginalFilename();
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
		try {
			outputStream.write(file.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		} finally {
			outputStream.close();
		}
		return filePath;
	}

	@Override
	public ProductDTO createCatalogue(ProductDTO product) {
		Product savedProduct = catalogueRepository.save(productMapper.toEntity(product));
		return productMapper.toDto(savedProduct);
	}
}
