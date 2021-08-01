package com.go2shop.catalogue.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.go2shop.common.exception.BussinessException;
import com.go2shop.common.exception.EmBusinessError;

@Service
@ComponentScan
public class DemoCatalogueService {
	
	public String getCatalogue(int id) throws BussinessException {
		if (id == 0) {
			throw new BussinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
		}
		return "Catalogue id " + id;
	}

}
