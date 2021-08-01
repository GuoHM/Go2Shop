package com.go2shop.catalogue.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.go2shop.common.exception.BusinessException;
import com.go2shop.common.exception.EmBusinessError;

@Service
@ComponentScan
public class DemoCatalogueService {
	
	public String getCatalogue(int id) throws BusinessException {
		if (id == 0) {
			throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		return "Catalogue id " + id;
	}

}
