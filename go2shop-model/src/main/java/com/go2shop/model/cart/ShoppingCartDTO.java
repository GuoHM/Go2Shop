package com.go2shop.model.cart;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

public class ShoppingCartDTO {

	@NotNull
	private Long id;

	private BigDecimal price;

	private BigDecimal discount;

	@NotNull
	private Long userId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ShoppingCartDTO [id=" + id + ", price=" + price + ", discount=" + discount + ", userId=" + userId + "]";
	}

}
