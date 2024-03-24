package com.tat.shoza.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_list_product_img")
public class ListProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "product_image")
	private String productImage;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_product")
	private Product product;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ListProductImage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ListProductImage(String productImage, Product product) {
		super();
		this.productImage = productImage;
		this.product = product;
	}
	
}
