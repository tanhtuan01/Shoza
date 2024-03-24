package com.tat.shoza.dto;

public class ProductDTO {

	private Long id;
	
	private String productName;
	
	private String productTitle;
	
	private Long productOldPrice;
	
	private int productDiscount;
	
	private Long productCurrentPrice;
	
	private int productQuantity;
	
	private String productImage;
	
	private String productDescription;
	
	private Long idCategory;
	
	private String categoryName;
	
	private int productSold;
	
	
	public int getProductSold() {
		return productSold;
	}

	public void setProductSold(int productSold) {
		this.productSold = productSold;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(Long idCategory) {
		this.idCategory = idCategory;
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

	public void setProductOldPrice(Long productPrice) {
		this.productOldPrice = productPrice;
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

	public void setProductCurrentPrice(Long productNewPrice) {
		this.productCurrentPrice = productNewPrice;
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

	public ProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", productName=" + productName + ", productTitle=" + productTitle
				+ ", productOldPrice=" + productOldPrice + ", productDiscount=" + productDiscount
				+ ", productCurrentPrice=" + productCurrentPrice + ", productQuantity=" + productQuantity
				+ ", productImage=" + productImage + ", productDescription=" + productDescription + ", idCategory="
				+ idCategory + "]";
	}

	public ProductDTO(String productName, String productTitle, Long productOldPrice, int productDiscount,
			Long productCurrentPrice, int productQuantity, String productImage, String productDescription,
			int productSold, String categoryName) {
		super();
		this.productName = productName;
		this.productTitle = productTitle;
		this.productOldPrice = productOldPrice;
		this.productDiscount = productDiscount;
		this.productCurrentPrice = productCurrentPrice;
		this.productQuantity = productQuantity;
		this.productImage = productImage;
		this.productDescription = productDescription;
		this.categoryName = categoryName;
		this.productSold = productSold;
	}

	public ProductDTO(Long id, String productName, String productTitle, Long productOldPrice, int productDiscount,
			Long productCurrentPrice, int productQuantity, String productImage, String productDescription,
			Long idCategory) {
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
		this.idCategory = idCategory;
	}

	public ProductDTO(String productName, String productTitle, Long productOldPrice, int productDiscount,
			Long productCurrentPrice, String productImage) {
		super();
		this.productName = productName;
		this.productTitle = productTitle;
		this.productOldPrice = productOldPrice;
		this.productDiscount = productDiscount;
		this.productCurrentPrice = productCurrentPrice;
		this.productImage = productImage;
	}

	public ProductDTO(Long id, String productName, String productTitle, Long productOldPrice, int productDiscount,
			Long productCurrentPrice, int productQuantity, String productImage, String productDescription,
			int productSold) {
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
		this.productSold = productSold;
	}

	public ProductDTO(Long id, String productName, String productTitle, String image) {
		super();
		this.id = id;
		this.productName = productName;
		this.productTitle = productTitle;
		this.productImage = image;
	}

	
}
