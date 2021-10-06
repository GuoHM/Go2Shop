package com.go2shop.model.cart;

import javax.validation.constraints.NotNull;

import com.go2shop.model.product.ProductDTO;

public class ShoppingCartProductDTO {

	private Long id;

	@NotNull
	private Long shoppingCartId;

	@NotNull
	private Long productId;

	@NotNull
	private int quantity;

	private ProductDTO product;
	
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

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

}
