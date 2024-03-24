package com.tat.shoza.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.dto.CategoryDTO;
import com.tat.shoza.model.Category;
import com.tat.shoza.repository.CategoryRepository;
import com.tat.shoza.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category save(Category category) {
		// TODO Auto-generated method stub
		return categoryRepository.save(category);
	}
	
	@Override
	public List<Category> list() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

	@Override
	public Category get(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.getById(id);
	}

	@Override
	public boolean existsByCategoryTitle(String title, Long id) {
		// TODO Auto-generated method stub
		if(id == 0) {
			return categoryRepository.existsByCategoryTitleEquals(title);
		}else {
			return categoryRepository.existsByCategoryTitleEqualsAndIdNot(title, id);
		}
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);
	}
	
	@Override
	public Category findById(Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.getById(id);
	}
	
	@Override
	public Category getCategoryByProductId(@Param("id_product") Long id) {
		// TODO Auto-generated method stub
		return categoryRepository.getCategoryByProductId(id);
	}
	
	@Override
	public Category findByCategoryTitle(String title) {
		// TODO Auto-generated method stub
		return categoryRepository.findByCategoryTitleEquals(title);
	}
	
	@Override
	public List<Category> getCategoryShowInIndex() {
		// TODO Auto-generated method stub
		return categoryRepository.getCategoryShowInIndex();
	}
}
