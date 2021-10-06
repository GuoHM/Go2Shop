package com.go2shop.model.product;

public class ProductRatingsDTO {

	private Long totalRatings;
	
	private Long noOfReviews;

	public ProductRatingsDTO(Long totalRatings, Long noOfReviews) {
		this.totalRatings = totalRatings;
		this.noOfReviews = noOfReviews;
	}
	
	public Long getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(Long totalRatings) {
		this.totalRatings = totalRatings;
	}

	public Long getNoOfReviews() {
		return noOfReviews;
	}

	public void setNoOfReviews(Long noOfReviews) {
		this.noOfReviews = noOfReviews;
	}
}
