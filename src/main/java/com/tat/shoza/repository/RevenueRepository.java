package com.tat.shoza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tat.shoza.model.Revenue;

public interface RevenueRepository extends JpaRepository<Revenue, Long>{

	@Query("select sum(r.totalPrice) from Revenue r")
	Long sumRevenue();
}
