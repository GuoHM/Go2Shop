package com.go2shop.order.service.feign;

import javax.validation.constraints.NotNull;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.model.cart.ShoppingCartProductDTO;
import com.go2shop.order.service.feign.fallback.CatalogueServiceFallback;

@FeignClient(value = "catalogue-service", fallback = CatalogueServiceFallback.class)
public interface CatalogueService {
	
	@PutMapping("/catalogue/deductProductStock/{id}")
	public ResponseEntity<Void> deductProductStock(
			@PathVariable("id") @NotNull Long id,
			@RequestBody(required = true) @NotNull ShoppingCartProductDTO cartProduct) throws BusinessException;
	
	@PutMapping("/catalogue/returnProductStock/{id}/{returnedQuantity}")
	public ResponseEntity<Void> returnProductStock(
			@PathVariable("id") @NotNull Long id, @PathVariable("returnedQuantity") @NotNull int quantity) throws BusinessException;
}
