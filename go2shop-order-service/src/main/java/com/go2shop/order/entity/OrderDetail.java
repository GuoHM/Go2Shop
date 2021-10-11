package com.go2shop.order.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TB_ORDER_DETAIL")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@NotNull
	@Column(name = "ORDER_DATE")
	private LocalDateTime orderDate;

	@Column(name = "ORDER_RECEIVED")
	private LocalDateTime orderReceived;

	@Column(name = "PAYMENT_DATE")
	private LocalDateTime paymentDate;

	@Column(name = "PAYMENT")
	private BigDecimal payment;

	@NotNull
	@Column(name = "PRODUCT_DEDUCT_QTY")
	private Integer deductQty;
	
	@ManyToOne
	@JoinColumn(name="TB_ORDER_ID", nullable = false)
	private Order order;

	@NotNull
	@Column(name = "TB_PRODUCT_ID")
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
