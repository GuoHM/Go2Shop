package com.go2shop.model.order;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.go2shop.model.cart.ShoppingCartProductDTO;

public class CreateOrderDTO {

	@NotNull
	private String paymentType;
	
	@NotNull
	private Long buyerId;

	@NotNull
	List<ShoppingCartProductDTO> cartProducts;

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public List<ShoppingCartProductDTO> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<ShoppingCartProductDTO> cartProducts) {
		this.cartProducts = cartProducts;
	}
}
