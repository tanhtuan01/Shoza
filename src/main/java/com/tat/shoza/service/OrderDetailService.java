package com.tat.shoza.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tat.shoza.model.OrderDetail;

public interface OrderDetailService {

	OrderDetail save(OrderDetail orderDetail);
	
	List<OrderDetail> listOrderDetailByOrder(Long id);
	
	Page<OrderDetail> pageListProductSoldToday(int day, int month, int year, Pageable pageable);
}
