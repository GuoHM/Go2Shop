package com.go2shop.catalogue.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.catalogue.entity.ProductSearchDTO;
import com.go2shop.catalogue.service.CatalogueService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.model.product.ProductDTO;

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
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		return this.catalogueService.getProductById(id)
				.map(product -> ResponseEntity.ok().body(product))
				.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping("/catalogue/search")
	public ResponseEntity<List<ProductDTO>> getCatalogueBySearchDTO(
			@RequestBody(required = false) ProductSearchDTO searchDTO, Pageable page) {
		Page<ProductDTO> results = catalogueService.searchProducts(searchDTO, page);
		HttpHeaders headers = new HttpHeaders();
        headers.add(TOTAL_COUNT, Long.toString(results.getTotalElements()));
		return new ResponseEntity<>(results.getContent(), headers, HttpStatus.OK);
	}
}
