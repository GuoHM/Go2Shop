package com.go2shop.catalogue.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.go2shop.catalogue.service.DemoCatalogueService;
import com.go2shop.common.controller.BaseController;
import com.go2shop.common.exception.BussinessException;

@RestController
@RequestMapping(value = "/catalogue/demo")
@RefreshScope
public class DemoCatalogueController extends BaseController {
	
	@Autowired
	private DemoCatalogueService catalogueService;
	
	@Value("${server.port}")
	private int serverPort;

	@GetMapping("/catalogueId/{catalogueId}")
	public String getCatalogue(@PathVariable("catalogueId") int catalogueId) throws BussinessException {
		return catalogueService.getCatalogue(catalogueId) + ", deployed port: " + serverPort;
	}

}