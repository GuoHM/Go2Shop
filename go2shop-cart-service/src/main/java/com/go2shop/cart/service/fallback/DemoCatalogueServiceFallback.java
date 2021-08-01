package com.go2shop.cart.service.fallback;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.go2shop.cart.service.DemoCatalogueService;
import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;

@Service
public class DemoCatalogueServiceFallback implements DemoCatalogueService {

	@Override
	public ResponseEntity<String> getCatalogue(String catalogueId) throws BusinessException {
		return ResponseEntity.badRequest().body("Sorry, something wrong when try to get catalogue with id " + catalogueId);
	}

}
