package com.go2shop.catalogue.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.go2shop.catalogue.service.CatalogueService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.model.product.ProductDTO;
import com.go2shop.model.product.ProductRatingsDTO;
import com.go2shop.model.product.ProductReviewDTO;
import com.go2shop.model.product.ProductSearchDTO;

@RestController
@RequestMapping(value = "/catalogue")
@RefreshScope
public class CatalogueController extends BaseController {

	@Autowired
	private CatalogueService catalogueService;
	private static final String TOTAL_COUNT = "X-Total-Count";
	@GetMapping("/catalogue")
	public ResponseEntity<List<ProductDTO>> getCatalogue() {
		return ResponseEntity.ok().body(this.catalogueService.getCatalogue());
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable("id") @NotNull Long id) {
		return this.catalogueService.getProductById(id).map(product -> ResponseEntity.ok().body(product))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/product/reviews/{id}")
	public ResponseEntity<List<ProductReviewDTO>> getProductReviewsByProductId(@PathVariable("id") @NotNull Long id, Pageable page) {
		Page<ProductReviewDTO> results = catalogueService.getProductReviews(id, page);
		HttpHeaders headers = new HttpHeaders();
        headers.add(TOTAL_COUNT, Long.toString(results.getTotalElements()));
		return new ResponseEntity<>(results.getContent(), headers, HttpStatus.OK);
	}
	
	@GetMapping("/product/ratings/{id}")
	public ResponseEntity<ProductRatingsDTO> getProductRatingsByProductId(@PathVariable("id") @NotNull Long id) {
		return ResponseEntity.ok().body(catalogueService.getProductRatings(id));
	}
	
	@PostMapping("/catalogue/search")
	public ResponseEntity<List<ProductDTO>> getCatalogueBySearchDTO(
			@RequestBody(required = false) ProductSearchDTO searchDTO, Pageable page) {
		Page<ProductDTO> results = catalogueService.searchProducts(searchDTO, page);
		HttpHeaders headers = new HttpHeaders();
        headers.add(TOTAL_COUNT, Long.toString(results.getTotalElements()));
		return new ResponseEntity<>(results.getContent(), headers, HttpStatus.OK);
	}

	@PostMapping("/image")
	public ResponseEntity<HashMap<String, String>> uploadImage(@RequestParam("image") MultipartFile file)
			throws BusinessException, IOException {
		HashMap<String, String> result = new HashMap<>();
		result.put("imageName", catalogueService.uploadImage(file));
		return ResponseEntity.ok().body(result);
	}

	@PostMapping("/catalogue")
	public ResponseEntity<ProductDTO> createCatalogue(@RequestBody ProductDTO product) {
		return ResponseEntity.ok().body(catalogueService.createCatalogue(product));
	}
	
	@PostMapping("/addProductReview")
	public ResponseEntity<ProductReviewDTO> addProductReview(@RequestBody(required = true) ProductReviewDTO review) {
		return ResponseEntity.ok().body(catalogueService.addProductReview(review));
	}
	
	@PutMapping("/deductProductStock/{id}")
	public ResponseEntity<Void> deductProductStock(
			@PathVariable("id") @NotNull Long id,
			@RequestBody(required = true) @NotNull ShoppingCartProductDTO cartProduct) throws BusinessException {
		catalogueService.deductProductStockById(id, cartProduct);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/returnProductStock/{id}/{returnedQuantity}")
	public ResponseEntity<Void> returnProductStock(
			@PathVariable("id") @NotNull Long id, @PathVariable("returnedQuantity") @NotNull int quantity) throws BusinessException {
		catalogueService.returnProductStockById(id, quantity);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/product/getRecommendedProducts")
	public ResponseEntity<List<ProductDTO>> getRecommendedProducts(Pageable page) {
		Page<ProductDTO> results = catalogueService.getRecommendedProductsByRatings(page);
		HttpHeaders headers = new HttpHeaders();
        headers.add(TOTAL_COUNT, Long.toString(results.getTotalElements()));
		return new ResponseEntity<>(results.getContent(), headers, HttpStatus.OK);
	}
}
