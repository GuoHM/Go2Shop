package com.go2shop.model.cart;

import javax.validation.constraints.NotNull;

public class UserToProductDTO {

	@NotNull
	private Long userId;
	
	@NotNull
	private Long productId;
	
	@NotNull
	private int quantity;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
