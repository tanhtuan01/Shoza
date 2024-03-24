package com.tat.shoza.dto;

import java.util.List;

public class CategoryDTO {

	private Long id;
	
	private String categoryName;
	
	private String categoryTitle;
	
	private String categoryImage;
	
	private int categoryStatus;

	public int getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(int categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryTitle() {
		return categoryTitle;
	}

	public void setCategoryTitle(String categoryTitle) {
		this.categoryTitle = categoryTitle;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public CategoryDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CategoryDTO(Long id, String categoryName, String categoryTitle, String categoryImage) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryTitle = categoryTitle;
		this.categoryImage = categoryImage;
	}

	public CategoryDTO(String categoryName, String categoryTitle, String categoryImage) {
		super();
		this.categoryName = categoryName;
		this.categoryTitle = categoryTitle;
		this.categoryImage = categoryImage;
	}

	

	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", categoryName=" + categoryName + ", categoryTitle=" + categoryTitle
				+ ", categoryImage=" + categoryImage + ", categoryStatus=" + categoryStatus + "]";
	}

	public CategoryDTO(Long id, String categoryName, String categoryTitle) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryTitle = categoryTitle;
	}

	public CategoryDTO(String categoryName, String categoryTitle) {
		super();
		this.categoryName = categoryName;
		this.categoryTitle = categoryTitle;
	}
	
	private List<ProductDTO> products;

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}

	public CategoryDTO(Long id, String categoryName, String categoryTitle, String categoryImage,
			List<ProductDTO> products) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryTitle = categoryTitle;
		this.categoryImage = categoryImage;
		this.products = products;
	}

	public String setStatus() {
		return (this.categoryStatus == 1) ? "Hiển thị" : "Không";
	}
	

	
}
