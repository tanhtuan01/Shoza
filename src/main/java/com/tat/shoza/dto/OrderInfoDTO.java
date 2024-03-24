package com.tat.shoza.dto;

public class OrderInfoDTO {

	private String name;
	
	private String phone;
	
	private String address;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public OrderInfoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "OrderInfoDTO [name=" + name + ", phone=" + phone + ", address=" + address + "]";
	}
	
	
	
}
