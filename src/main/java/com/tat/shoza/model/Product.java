package com.tat.shoza.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_title")
	private String productTitle;
	
	@Column(name = "product_old_price")
	private Long productOldPrice;
	
	@Column(name = "product_discount")
	private int productDiscount;
	
	@Column(name = "product_current_price")
	private Long productCurrentPrice;
	
	@Column(name = "product_quantity")
	private int productQuantity;
	
	@Column(name = "product_image")
	private String productImage;
	
	@Column(name = "product_sold")
	private int productSold;
	
	@Column(name = "product_description")
	private String productDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_category")
	private Category category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ListProductImage> listProductImages;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Cart> carts;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CommentStatus> commentStatus;
	
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<CommentStatus> getCommentStatus() {
		return commentStatus;
	}

	public void setCommentStatus(List<CommentStatus> commentStatus) {
		this.commentStatus = commentStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductTitle() {
		return productTitle;
	}

	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}

	public Long getProductOldPrice() {
		return productOldPrice;
	}

	public void setProductOldPrice(Long productOldPrice) {
		this.productOldPrice = productOldPrice;
	}

	public int getProductDiscount() {
		return productDiscount;
	}

	public void setProductDiscount(int productDiscount) {
		this.productDiscount = productDiscount;
	}

	public Long getProductCurrentPrice() {
		return productCurrentPrice;
	}

	public void setProductCurrentPrice(Long productCurrentPrice) {
		this.productCurrentPrice = productCurrentPrice;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public Product(Long id, String productName, String productTitle, Long productOldPrice, int productDiscount,
			Long productCurrentPrice, int productQuantity, String productImage, String productDescription) {
		super();
		this.id = id;
		this.productName = productName;
		this.productTitle = productTitle;
		this.productOldPrice = productOldPrice;
		this.productDiscount = productDiscount;
		this.productCurrentPrice = productCurrentPrice;
		this.productQuantity = productQuantity;
		this.productImage = productImage;
		this.productDescription = productDescription;
	}

	public List<ListProductImage> getListProductImages() {
		return listProductImages;
	}

	public void setListProductImages(List<ListProductImage> listProductImages) {
		this.listProductImages = listProductImages;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", productName=" + productName + ", productTitle=" + productTitle
				+ ", productOldPrice=" + productOldPrice + ", productDiscount=" + productDiscount
				+ ", productCurrentPrice=" + productCurrentPrice + ", productQuantity=" + productQuantity
				+ ", productImage=" + productImage + ", productDescription=" + productDescription + ", category="
				+ category + "]";
	}

	public int getProductSold() {
		return productSold;
	}

	public void setProductSold(int productSold) {
		this.productSold = productSold;
	}

	public void setData(String productName, String productTitle, Long productOldPrice, int productDiscount,
			Long productCurrentPrice, int productQuantity, String productImage, String productDescription,
			Category category) {
		this.productName = productName;
		this.productTitle = productTitle;
		this.productOldPrice = productOldPrice;
		this.productDiscount = productDiscount;
		this.productCurrentPrice = productCurrentPrice;
		this.productQuantity = productQuantity;
		this.productImage = productImage;
		this.productDescription = productDescription;
		this.category = category;
	}

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}
	
	
	
}
