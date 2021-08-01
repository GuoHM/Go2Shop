package com.go2shop.cart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;

@Service
public class DemoCartService {

	@Autowired
	DemoCatalogueService demoCatalogueService;

	public String getCartById(String cartId) throws BusinessException {
		if (Integer.parseInt(cartId) == 0) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		return "Get cart with Id " + cartId;
	}

	public String getCartWithCatalogue(String cartId, String catalogueId) throws BusinessException {
		if (Integer.parseInt(cartId) == 0) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("Get cart with Id " + cartId);
		ResponseEntity<String> res = demoCatalogueService.getCatalogue(catalogueId);
		if (res.getStatusCode() == HttpStatus.BAD_REQUEST) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "fallback");
		}
		sb.append(res.getBody());
		return sb.toString();
	}

}
