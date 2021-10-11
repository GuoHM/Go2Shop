package com.go2shop.catalogue.service.impl;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.go2shop.catalogue.entity.Product;
import com.go2shop.catalogue.entity.ProductReview;
import com.go2shop.catalogue.entity.RecommendedProduct;
import com.go2shop.catalogue.repository.CatalogueRepository;
import com.go2shop.catalogue.repository.ProductReviewRepository;
import com.go2shop.catalogue.service.CatalogueService;
import com.go2shop.catalogue.service.feign.UserService;
import com.go2shop.catalogue.service.mapper.ProductMapper;
import com.go2shop.catalogue.service.mapper.ProductReviewMapper;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.product.ProductDTO;
import com.go2shop.model.product.ProductRatingsDTO;
import com.go2shop.model.product.ProductReviewDTO;
import com.go2shop.model.product.ProductSearchDTO;
import com.go2shop.model.user.UserDTO;

@Service
public class CatalogueServiceImpl implements CatalogueService {

	@Value("${img.upload-path}")
	private String uploadPath;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private ProductReviewMapper productReviewMapper;
	
	@Autowired
	private CatalogueRepository catalogueRepository;
	
	@Autowired
	private ProductReviewRepository productReviewRepository;

	@Override
	public List<ProductDTO> getCatalogue() {
		return this.catalogueRepository.findAll().stream().map(productMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Optional<ProductDTO> getProductById(Long id) {
//		Optional<ProductDTO> productOpt = this.catalogueRepository.findById(id).map(productMapper::toDto);
//		if(productOpt.isPresent()) {
//			List<ProductReview> reviews = productReviewRepository.findAllByProductId(productOpt.get().getId());
//			productOpt.get().setProductReviews(reviews.stream().map(productReviewMapper::toDto).collect(Collectors.toList()));
//		}
		return this.catalogueRepository.findById(id).map(productMapper::toDto);
	}
	
	@Override
	public Page<ProductReviewDTO> getProductReviews(Long id, Pageable page) {
		Page<ProductReview> result = productReviewRepository.findAllByProductId(id, page);
		return result.map(productReview -> {
			ProductReviewDTO review = productReviewMapper.toDto(productReview);
			if(productReview.getUserId() != null) {
				ResponseEntity<UserDTO> response = userService.getUserById(productReview.getUserId());
				if(response.hasBody()) {
					review.setUser(response.getBody());
				}
			}
			return review;
		});
	}
	
	@Override
	public ProductRatingsDTO getProductRatings(Long id) {
		return productReviewRepository.findProductRatings(id);
	}
	
	@Override
	public Page<ProductDTO> searchProducts(ProductSearchDTO searchDTO, Pageable page) {
		Specification<Product> spec = getSearchProductSpec(searchDTO);
		Page<Product> result = catalogueRepository.findAll(spec, page);
		
		return result.map(product -> {
			return productMapper.toDto(product);
		});
	}
	
	private Specification<Product> getSearchProductSpec(ProductSearchDTO searchDTO) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			Predicate p1 = cb.greaterThan(root.get("stock"), 0);
			predicates.add(p1);
			
			if (searchDTO == null) {
				Predicate[] p = new Predicate[predicates.size()];
				return cb.and(predicates.toArray(p));
			}
			
			if (!StringUtils.isBlank(searchDTO.getSearchTerm())) {
				Predicate p2 = cb.like(root.get("name"), "%" + searchDTO.getSearchTerm() + "%");
				predicates.add(p2);
				Predicate p3 = cb.like(root.get("description"), "%" + searchDTO.getSearchTerm() + "%");
				predicates.add(p3);
			}
			query.distinct(true);
			Predicate[] p = new Predicate[predicates.size()];
			return cb.and(predicates.toArray(p));
		};
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
		String fileName = UUID.randomUUID() + file.getOriginalFilename();
		String filePath = uploadPath + fileName;
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
		try {
			outputStream.write(file.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		} finally {
			outputStream.close();
		}
		return fileName;
	}

	@Override
	public ProductDTO createCatalogue(ProductDTO product) {
		Product productToSave = productMapper.toEntity(product);
		productToSave.getProductImages().stream().forEach(productImage -> productImage.setProduct(productToSave));
		return productMapper.toDto(catalogueRepository.save(productToSave));
	}
	
	@Override
	public ProductReviewDTO addProductReview(ProductReviewDTO review) {
		return productReviewMapper.toDto(productReviewRepository.save(productReviewMapper.toEntity(review)));
	}
	
	@Override
	public void deductProductStockById(Long id, ShoppingCartProductDTO cartProduct) throws BusinessException {
		Optional<Product> productOpt = catalogueRepository.findById(id);
		if(productOpt.isPresent()) {
			if(cartProduct != null) {
				if(cartProduct.getQuantity() > productOpt.get().getStock()) {
					throw new BusinessException(EmBusinessError.NOT_ENOUGH_PRODUCT_STOCK);
				} else {
					Product product = productOpt.get();
					product.setStock(product.getStock() - cartProduct.getQuantity());
					catalogueRepository.save(product);
				}
			}
		} else {
			throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
		}
	}
	
	@Override
	public void returnProductStockById(Long id, int quantity) throws BusinessException {
		Optional<Product> productOpt = catalogueRepository.findById(id);
		if(productOpt.isPresent()) {
			Product product = productOpt.get();
			product.setStock(product.getStock() + quantity);
			catalogueRepository.save(product);
		} else {
			throw new BusinessException(EmBusinessError.PRODUCT_NOT_EXIST);
		}
	}
	
	@Override
	public Page<ProductDTO> getRecommendedProductsByRatings(Pageable page) {
		Page<RecommendedProduct> result = catalogueRepository.getRecommendationsByRatings(page);
		return result.map(product -> {
			return productMapper.toDto(product.getProduct());
		});
	}
}
