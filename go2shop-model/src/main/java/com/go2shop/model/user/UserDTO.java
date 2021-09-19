package com.go2shop.model.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDTO {

	private Long id;

	@NotBlank
	@Size(max = 16)
	private String cardNumber;

	@NotBlank
	@Size(max = 50)
	private String name;

	@NotBlank
	@Size(max = 5)
	private String expiry;

	@NotBlank
	@Size(max = 100)
	private String address;

	@NotBlank
	@Size(max = 8)
	private String contactDetail;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactDetail() {
		return contactDetail;
	}

	public void setContactDetail(String contactDetail) {
		this.contactDetail = contactDetail;
	}

}
