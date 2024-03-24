package com.tat.shoza.dto;

public class ProductSoldDTO {

	private ProductDTO productDTO;
	
	private int count;

	public ProductDTO getProductDTO() {
		return productDTO;
	}

	public void setProductDTO(ProductDTO productDTO) {
		this.productDTO = productDTO;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public ProductSoldDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
