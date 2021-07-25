package com.go2shop.catalogue.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/catalogue/demo")
@RefreshScope
public class DemoCatalogueController {
	
	@Value("${server.port}")
	private int serverPort;

	@GetMapping("/catalogueId/{catalogueId}")
	public String getCatalogue(@PathVariable("catalogueId") String catalogueId) {
		return "This is form catalogue service with catalogue: " + catalogueId + ", deployed port: " + serverPort;
	}

}
