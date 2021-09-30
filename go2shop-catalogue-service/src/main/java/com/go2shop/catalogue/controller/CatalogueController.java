package com.go2shop.catalogue.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.go2shop.catalogue.service.CatalogueService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;
import com.go2shop.model.product.ProductDTO;
import com.go2shop.model.product.ProductImageDTO;

@RestController
@RequestMapping(value = "/catalogue")
@RefreshScope
public class CatalogueController extends BaseController {

	@Autowired
	private CatalogueService catalogueService;

	@GetMapping("/catalogue")
	public ResponseEntity<List<ProductDTO>> getCatalogue() {
		return ResponseEntity.ok().body(this.catalogueService.getCatalogue());
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
		return this.catalogueService.getProductById(id).map(product -> ResponseEntity.ok().body(product))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/image")
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file)
			throws BusinessException, IOException {
		return ResponseEntity.ok().body(catalogueService.uploadImage(file));
	}

	@GetMapping("/catalogue")
	public ResponseEntity<ProductDTO> createCatalogue(@RequestBody ProductDTO product) {
		return ResponseEntity.ok().body(catalogueService.createCatalogue(product));
	}

}
