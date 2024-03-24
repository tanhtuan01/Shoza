package com.tat.shoza.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tat.shoza.dto.ProductDTO;

@Entity
@Table(name = "tb_categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "category_name")
	private String categoryName;
	
	@Column(name = "category_title")
	private String categoryTitle;
	
	@Column(name = "category_image")
	private String categoryImage;
	
	@Column(name = "category_status")
	private int categoryStatus;
	
	public int getCategoryStatus() {
		return categoryStatus;
	}

	public void setCategoryStatus(int categoryStatus) {
		this.categoryStatus = categoryStatus;
	}

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Product> products;
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", categoryTitle=" + categoryTitle
				+ ", categoryImage=" + categoryImage + ", categoryStatus=" + categoryStatus + "]";
	}

	public Category(String categoryName, String categoryTitle, String categoryImage) {
		super();
		this.categoryName = categoryName;
		this.categoryTitle = categoryTitle;
		this.categoryImage = categoryImage;
	}

	public Category(Long id, String categoryName, String categoryTitle, String categoryImage) {
		super();
		this.id = id;
		this.categoryName = categoryName;
		this.categoryTitle = categoryTitle;
		this.categoryImage = categoryImage;
	}

	public Category(Long id, String categoryName, String categoryTitle, String categoryImage, List<Product> products) {
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
