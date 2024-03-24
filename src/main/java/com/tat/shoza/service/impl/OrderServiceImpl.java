package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.Order;
import com.tat.shoza.repository.OrderRepository;
import com.tat.shoza.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	
	@Override
	public Long saveAndGetId(Order order) {
		// TODO Auto-generated method stub
		Order saveIdOrder = orderRepository.saveAndFlush(order);
		return saveIdOrder.getId();
	}
	
	@Override
	public Order getById(Long id) {
		// TODO Auto-generated method stub
		return orderRepository.getById(id);
	}

	@Override
	public Page<Order> pageListOrderSuccess(Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.pageListOrderSuccess(pageable);
	}
	
	@Override
	public Page<Order> pageListOrder(Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.findAll(pageable);
	}
	
	@Override
	public Page<Order> pageListPriceASC(Pageable pageable) {
		// TODO Auto-generated method stub
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("order_total_price").ascending());
		return orderRepository.findAll(sortedPageable);
	}

	@Override
	public Page<Order> pageListPriceDESC(Pageable pageable) {
		// TODO Auto-generated method stub
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("order_total_price").descending());
		return orderRepository.findAll(sortedPageable);
	}
	
	@Override
	public Page<Order> pageListOrderWithStatus(@Param("stt")int stt, Pageable pageable) {
		// TODO Auto-generated method stub
		return  orderRepository.pageListOrderWithStatus(stt, pageable);
	}

	@Override
	public Order save(Order order) {
		// TODO Auto-generated method stub
		return orderRepository.save(order);
	}
	
	@Override
	public List<Order> getOrderByUser(@Param("iduser")Long id) {
		// TODO Auto-generated method stub
		return orderRepository.getOrderByUser(id);
	}
	
	@Override
	public List<Order> getOrderSuccessByUser(@Param("iduser")Long id) {
		// TODO Auto-generated method stub
		return orderRepository.getOrderSuccessByUser(id);
	}
	
	@Override
	public List<Order> getOrderWaitByUser(Long id) {
		// TODO Auto-generated method stub
		return orderRepository.getOrderWaitByUser(id);
	}
	
	@Override
	public List<Order> getOrderCancelByUser(Long id) {
		// TODO Auto-generated method stub
		return orderRepository.getOrderCancelByUser(id);
	}
	
	@Override
	public Long countSuccessOrder() {
		// TODO Auto-generated method stub
		return orderRepository.countSuccessOrder();
	}
	
	@Override
	public Long countFailOrder() {
		// TODO Auto-generated method stub
		return orderRepository.countFailOrder();
	}
	
	@Override
	public Page<Order> pageListOrderSuccessDate(@Param("day")int day,@Param("month") int month,@Param("year") int year, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.pageListOrderSuccessDate(day, month, year, pageable);
	}
	
	@Override
	public Long orderTotalPriceSuccessToday(@Param("day")int day,@Param("month") int month,@Param("year") int year) {
		// TODO Auto-generated method stub
		return orderRepository.orderTotalPriceSuccessToday(day, month, year);
	}
	
	@Override
	public Page<Order> pageListOrderFailDate(@Param("day")int day,@Param("month") int month,@Param("year") int year, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.pageListOrderFailDate(day, month, year, pageable);
	}
	
	@Override
	public Page<Order> pageListByDate(@Param("day")int day,@Param("month") int month,@Param("year") int year, Pageable pageable) {
		// TODO Auto-generated method stub
		return orderRepository.pageListByDate(day, month, year, pageable);
	}
}
