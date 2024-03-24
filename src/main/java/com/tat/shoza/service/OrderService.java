package com.tat.shoza.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tat.shoza.model.Order;

public interface OrderService {

	Long saveAndGetId(Order order);
	
	Order getById(Long id);
	
	Page<Order> pageListOrderSuccess(Pageable pageable);
	
	Page<Order> pageListOrder(Pageable pageable);
	
	Page<Order> pageListPriceASC(Pageable pageable);
	
	Page<Order> pageListPriceDESC(Pageable pageable);
	
	Page<Order> pageListOrderWithStatus(int stt, Pageable pageable);
	
	Order save(Order order);
	
	List<Order> getOrderByUser(Long id);
	
	List<Order> getOrderSuccessByUser(Long id);
	
	List<Order> getOrderWaitByUser(Long id);
	
	List<Order> getOrderCancelByUser(Long id);
	
	Long countSuccessOrder();
	
	Long countFailOrder();
	
	Page<Order> pageListOrderSuccessDate(int day, int month, int year, Pageable pageable);
	
	Long orderTotalPriceSuccessToday(int day, int month, int year);
	
	Page<Order> pageListOrderFailDate(int day, int month, int year, Pageable pageable);
	
	Page<Order> pageListByDate(int day, int month, int year, Pageable pageable);
}
