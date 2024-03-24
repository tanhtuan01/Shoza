package com.tat.shoza.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.Product;

public interface ProductService {

	List<Product> listProductByCategory(Long id);
	
	List<Product> list();
	
	Long saveAndGetId(Product product);
	
	Product save(Product product);
	
	Product getById(Long id);
	
	boolean existsByProductTitle(String productTitle, Long id);
	
	List<Product> listProductSortDESC();
	
	List<Product> listProductSortASC();
	
	List<Product> sortByColumnDESC(String column);
	
	List<Product> sortByColumnASC(String column);
	
	void delete(Long id);
	
	List<Product> listProductByCategoryAndLimit(Long id, int limit);
	
	Page<Product> pageListProductByCategory(Long id, Pageable pageable);
	
	Product getByCategoryTitle(String title);
	
	Page<Product> pageListByName(String title, Pageable pageable);
	
	List<Product> findByName(String name);
	
	Long countProduct();
	
	Long countProductSold();
	
	Page<Product> pageTopListProductSold(Pageable pageable);
}
