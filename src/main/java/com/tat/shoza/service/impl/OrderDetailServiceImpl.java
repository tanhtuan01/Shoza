package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.OrderDetail;
import com.tat.shoza.repository.OrderDetailRepository;
import com.tat.shoza.service.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

	@Autowired
	private OrderDetailRepository orderDetailRepository;
	
	@Override
	public OrderDetail save(OrderDetail orderDetail) {
		// TODO Auto-generated method stub
		return orderDetailRepository.save(orderDetail);
	}
	
	@Override
	public List<OrderDetail> listOrderDetailByOrder(@Param("id_order")Long id) {
		// TODO Auto-generated method stub
		return orderDetailRepository.listOrderDetailByOrder(id);
	}
	
	@Override
	public Page<OrderDetail> pageListProductSoldToday(@Param("day")int day, @Param("month")int month, @Param("year") int year, Pageable pageable){
		// TODO Auto-generated method stub
		return orderDetailRepository.pageListProductSoldToday(day, month, year, pageable);
	}
}
