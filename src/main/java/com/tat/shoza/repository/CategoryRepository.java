package com.tat.shoza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.Category;
import com.tat.shoza.model.Product;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	boolean existsByCategoryTitleEquals(String title);
	
	boolean existsByCategoryTitleEqualsAndIdNot(String title, Long id);
	
	// SELECT c FROM Category c Where c.products.id = :id_product
	@Query("SELECT c FROM Category c JOIN c.products p WHERE p.id = :id_product")
	Category getCategoryByProductId(@Param("id_product") Long id);
	
	Category findByCategoryTitleEquals(String title);
	
	@Query("select c from Category c where c.categoryStatus = 1")
	List<Category> getCategoryShowInIndex();
}
