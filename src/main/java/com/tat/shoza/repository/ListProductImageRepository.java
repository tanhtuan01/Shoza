package com.tat.shoza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.ListProductImage;

public interface ListProductImageRepository extends JpaRepository<ListProductImage, Long>{

	@Query("select i from ListProductImage i where i.product.id = :id_product")
	List<ListProductImage> listByProductId(@Param("id_product") Long id);
	
}
