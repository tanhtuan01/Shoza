package com.tat.shoza.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

	@Query("select o from Order o where o.orderStatus = :stt")
	Page<Order> pageListOrderWithStatus(@Param("stt")int stt, Pageable pageable);
	
	@Query("select o from Order o where o.user.id = :iduser")
	List<Order> getOrderByUser(@Param("iduser") Long id);
	
	@Query("select o from Order o where o.user.id = :iduser AND o.orderStatus = 3 OR o.orderStatus = 6")
	List<Order> getOrderSuccessByUser(@Param("iduser") Long id);
	
	@Query("select o from Order o where o.user.id = :iduser AND o.orderStatus = 2 OR o.orderStatus = 1 OR o.orderStatus = 0 OR o.orderStatus = 5")
	List<Order> getOrderWaitByUser(@Param("iduser") Long id);
	
	@Query("select o from Order o where o.user.id = :iduser AND o.orderStatus = 4")
	List<Order> getOrderCancelByUser(@Param("iduser") Long id);
	
	@Query("select count(o.id) from Order o where o.orderStatus = 3 ")
	Long countSuccessOrder();
	
	@Query("select count(o.id) from Order o where o.orderStatus = 4 ")
	Long countFailOrder();
	
	@Query("select o from Order o where o.orderStatus = 3 order by o.id DESC")
	Page<Order> pageListOrderSuccess(Pageable pageable);
	
	@Query("select o from Order o where o.orderStatus = 3 AND DAY(o.orderStatusAt) = :day AND MONTH(o.orderStatusAt) = :month AND YEAR(o.orderStatusAt) = :year")
	Page<Order> pageListOrderSuccessDate(@Param("day")int day, @Param("month")int month, @Param("year") int year, Pageable pageable);

	@Query("select sum(o.orderTotalPrice) from Order o where o.orderStatus = 3 AND DAY(o.orderStatusAt) = :day AND MONTH(o.orderStatusAt) = :month AND YEAR(o.orderStatusAt) = :year")
	Long orderTotalPriceSuccessToday(@Param("day")int day, @Param("month")int month, @Param("year") int year);
	
	@Query("select o from Order o where o.orderStatus = 4 AND DAY(o.orderStatusAt) = :day AND MONTH(o.orderStatusAt) = :month AND YEAR(o.orderStatusAt) = :year")
	Page<Order> pageListOrderFailDate(@Param("day")int day, @Param("month")int month, @Param("year") int year, Pageable pageable);

	@Query("select o from Order o where DAY(o.orderStatusAt) = :day AND MONTH(o.orderStatusAt) = :month AND YEAR(o.orderStatusAt) = :year")
	Page<Order> pageListByDate(@Param("day")int day, @Param("month")int month, @Param("year") int year, Pageable pageable);
}
