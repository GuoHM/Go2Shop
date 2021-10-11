package com.go2shop.catalogue.entity;

public class RecommendedProduct {

	private Product product;

	private Long totalRatings;

	public Product getProduct() {
		return product;
	}

	public RecommendedProduct(Product product, Long ratings) {
		this.product = product;
		this.totalRatings = ratings;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(Long totalRatings) {
		this.totalRatings = totalRatings;
	}
}
