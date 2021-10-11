package com.go2shop.cart.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_SHOPPING_CART_has_TB_PRODUCT")
public class ShoppingCartProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Column(name = "TB_SHOPPING_CART_ID")
	private Long shoppingCartId;

	@NotNull
	@Column(name = "TB_PRODUCT_ID")
	private Long productId;

	@NotNull
	@Column(name = "Quantity")
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(Long shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
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
