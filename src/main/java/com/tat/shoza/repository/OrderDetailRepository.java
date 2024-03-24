package com.tat.shoza.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

	@Query("select o from OrderDetail o where o.order.id = :id_order")
	List<OrderDetail> listOrderDetailByOrder(@Param("id_order")Long id);
	
	
	@Query("select o from OrderDetail o where DAY(o.order.orderStatusAt) = :day AND MONTH(o.order.orderStatusAt) = :month AND YEAR(o.order.orderStatusAt) = :year GROUP BY o.product.id")
	Page<OrderDetail> pageListProductSoldToday(@Param("day")int day, @Param("month")int month, @Param("year") int year, Pageable pageable);
}
