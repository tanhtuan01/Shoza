package com.tat.shoza.service;

import java.util.List;
import java.util.Optional;

import com.tat.shoza.dto.CategoryDTO;
import com.tat.shoza.model.Category;

public interface CategoryService {

	Category save(Category category);
	
	List<Category> list();
	
	Category get(Long id);
	
	boolean existsByCategoryTitle(String title, Long id);
	
	void delete(Long id);
	
	Category findById(Long id);
	
	Category getCategoryByProductId(Long id);
	
	Category findByCategoryTitle(String title);
	
	List<Category> getCategoryShowInIndex();
}
