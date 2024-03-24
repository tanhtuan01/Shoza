package com.tat.shoza.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	@Query("select p from Product p where p.category.id = :id_category")
	List<Product> listProductByCategory(@Param("id_category") Long id);
	
	@Query("select p from Product p where p.category.id = :id_category")
	Page<Product> getListProductByCategory(@Param("id_category") Long id, Pageable pageable);
	
	boolean existsByProductTitleEquals(String productTitle);
	
	boolean existsByProductTitleEqualsAndIdNot(String productTitle, Long id);
	
	@Query(nativeQuery = true,value = "select * from tb_products p where p.id_category = :id_category limit :limit")
	List<Product> listProductByCategoryAndLimit(@Param("id_category")Long id, @Param("limit") int limit);
	
	Product findByProductTitleEquals(String title);
	
	Page<Product> findByProductNameContaining(String productName, Pageable pageable);
	
	List<Product> findByProductNameContaining(String productName);
	
	@Query("select sum(p.productSold) from Product p")
	Long countProductSold();
	
	@Query("select p from Product p where p.productSold > 0 order by p.productSold DESC")
	Page<Product> pageTopListProductSold(Pageable pageable);
}
