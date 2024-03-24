package com.tat.shoza.dto;

public class OrderDetailDTO {

	private int quantity;
	
	private Long totalPrice;
	
	private String productName;
	
	private String image;

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public OrderDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
