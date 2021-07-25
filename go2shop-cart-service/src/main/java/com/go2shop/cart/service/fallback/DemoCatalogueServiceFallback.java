package com.go2shop.cart.service.fallback;

import org.springframework.stereotype.Service;

import com.go2shop.cart.service.DemoCatalogueService;

@Service
public class DemoCatalogueServiceFallback implements DemoCatalogueService {

	@Override
	public String getCatalogue(String catalogueId) {
		return "Sorry, something wrong when try to get catalogue with id " + catalogueId;
	}

}
