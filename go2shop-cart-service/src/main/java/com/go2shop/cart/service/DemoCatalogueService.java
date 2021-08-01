package com.go2shop.cart.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.go2shop.cart.service.fallback.DemoCatalogueServiceFallback;
import com.go2shop.common.exception.BusinessException;

@FeignClient(value = "catalogue-service", fallback = DemoCatalogueServiceFallback.class)
public interface DemoCatalogueService {
	
	@GetMapping("/catalogue/demo/catalogueId/{catalogueId}")
	ResponseEntity<String> getCatalogue(@PathVariable("catalogueId") String catalogueId) throws BusinessException;

}
