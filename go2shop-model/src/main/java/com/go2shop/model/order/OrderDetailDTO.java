package com.go2shop.model.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderDetailDTO {

	private Long id;

	private LocalDateTime orderDate;

	private LocalDateTime orderReceived;

	private LocalDateTime paymentDate;

	private BigDecimal payment;

	private Integer deductQty;
	
	private OrderDTO order;

	private Long productID;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDateTime getOrderReceived() {
		return orderReceived;
	}

	public void setOrderReceived(LocalDateTime orderReceived) {
		this.orderReceived = orderReceived;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPayment() {
		return payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public Integer getDeductQty() {
		return deductQty;
	}

	public void setDeductQty(Integer deductQty) {
		this.deductQty = deductQty;
	}

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

}
