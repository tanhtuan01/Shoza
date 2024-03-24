package com.tat.shoza.dto;

public class ItemDTO {

	private Long product;
	
	private int quantity;
	
	private Long price;

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public ItemDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "ItemDTO [product=" + product + ", quantity=" + quantity + ", price=" + price + "]";
	}

	
}
