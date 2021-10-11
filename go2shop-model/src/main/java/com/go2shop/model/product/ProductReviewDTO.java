package com.go2shop.model.product;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.go2shop.model.user.UserDTO;

public class ProductReviewDTO {

	private Long id;

	@NotNull
	private Long userId;
	
	@Size(max = 255)
	private String review;

	@NotNull
	private int rating;
	
	@NotNull
	private ProductDTO product;

	private UserDTO user;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}
}
