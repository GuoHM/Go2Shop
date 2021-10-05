package com.go2shop.model.product;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProductDTO {

	@NotNull
	private Long id;

	@NotBlank
	@Size(max = 100)
	private String name;

	@NotBlank
	@Size(max = 255)
	private String description;

	@NotNull
	@Digits(integer = 4, fraction = 2)
	private BigDecimal price;

	@NotNull
	private int stock;

	@NotNull
	private Long userID;

	List<ProductImageDTO> productImages;
	
	List<ProductReviewDTO> productReviews;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public List<ProductImageDTO> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<ProductImageDTO> productImages) {
		this.productImages = productImages;
	}

	public List<ProductReviewDTO> getProductReviews() {
		return productReviews;
	}

	public void setProductReviews(List<ProductReviewDTO> productReviews) {
		this.productReviews = productReviews;
	}

}
