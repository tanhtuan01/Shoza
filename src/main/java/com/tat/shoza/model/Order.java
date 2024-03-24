package com.tat.shoza.model;

import java.sql.Date;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "tb_orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "order_user_name")
	private String orderUserName;
	
	@Column(name = "order_total_price")
	private Long orderTotalPrice;
	
	@Column(name = "order_total_item")
	private int orderTotalItem;
	
	@Column(name = "order_phone")
	private String orderPhone;
	
	@Column(name = "order_address")
	private String orderAddess;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_user")
	private User user;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderDetail> listOrderDetails;
	
	@Column(name = "order_status")
	private int orderStatus;
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Revenue revenue;
	
	@LastModifiedDate
	@Column(name = "order_status_at")
	private String orderStatusAt;
	
	@Column(name = "order_code")
	private String orderCode;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_payment")
	private Payment payment;
	
	@Column(name = "bank_code")
	private String bankCode;

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getOrderStatusAt() {
		return orderStatusAt;
	}

	public void setOrderStatusAt(String orderStatusAt) {
		this.orderStatusAt = orderStatusAt;
	}

	public Revenue getRevenue() {
		return revenue;
	}

	public void setRevenue(Revenue revenue) {
		this.revenue = revenue;
	}

	public List<OrderDetail> getListOrderDetails() {
		return listOrderDetails;
	}

	public void setListOrderDetails(List<OrderDetail> listOrderDetails) {
		this.listOrderDetails = listOrderDetails;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderUserName() {
		return orderUserName;
	}

	public void setOrderUserName(String orderUserName) {
		this.orderUserName = orderUserName;
	}

	public Long getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(Long orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public int getOrderTotalItem() {
		return orderTotalItem;
	}

	public void setOrderTotalItem(int orderTotalItem) {
		this.orderTotalItem = orderTotalItem;
	}

	public String getOrderPhone() {
		return orderPhone;
	}

	public void setOrderPhone(String orderPhone) {
		this.orderPhone = orderPhone;
	}

	public String getOrderAddess() {
		return orderAddess;
	}

	public void setOrderAddess(String orderAddess) {
		this.orderAddess = orderAddess;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String orderUserName, Long orderTotalPrice, int orderTotalItem, String orderPhone, String orderAddess,
			User user) {
		super();
		this.orderUserName = orderUserName;
		this.orderTotalPrice = orderTotalPrice;
		this.orderTotalItem = orderTotalItem;
		this.orderPhone = orderPhone;
		this.orderAddess = orderAddess;
		this.user = user;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	public String getStatus() {
		switch (this.orderStatus) {
		case 0: 
			return "Chưa xác nhận";
		case 1:
			return "Đang chuyển giao cho vận chuyển";
		case 2:
			return "Đang vận chuyển";
		case 3:
			return "Giao thành công";
		case 4: 
			return "Giao thất bại";
		case 5: 
			return "Đã thanh toán-chưa nhận hàng";
		case 6:
			return "Đã nhận hàng";
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.orderStatus);
		}

	}
	
}
