package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.Product;
import com.tat.shoza.repository.ProductRepository;
import com.tat.shoza.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<Product> listProductByCategory(@Param("id_danhmuc")Long id) {
		// TODO Auto-generated method stub
		return productRepository.listProductByCategory(id);
	}
	
	@Override
	public List<Product> list() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}
	
	@Override
	public Long saveAndGetId(Product product) {
		// TODO Auto-generated method stub
		Product saveProduct = productRepository.saveAndFlush(product);
		return saveProduct.getId();
	}
	
	@Override
	public Product save(Product product) {
		// TODO Auto-generated method stub
		return productRepository.save(product);
	}
	
	@Override
	public Product getById(Long id) {
		// TODO Auto-generated method stub
		return productRepository.getById(id);
	}
	
	@Override
	public boolean existsByProductTitle(String productTitle, Long id) {
		// TODO Auto-generated method stub
		if(id == 0) {
			return productRepository.existsByProductTitleEquals(productTitle);
		}
		else {
			return productRepository.existsByProductTitleEqualsAndIdNot(productTitle, id);
		}
	}
	
	@Override
	public List<Product> listProductSortDESC() {
		// TODO Auto-generated method stub
		return productRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	}
	
	@Override
	public List<Product> listProductSortASC() {
		// TODO Auto-generated method stub
		return productRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	@Override
	public List<Product> sortByColumnDESC(String column) {
		// TODO Auto-generated method stub
		return productRepository.findAll(Sort.by(Sort.Direction.DESC, column));
	}
	
	@Override
	public List<Product> sortByColumnASC(String column) {
		// TODO Auto-generated method stub
		return productRepository.findAll(Sort.by(Sort.Direction.ASC, column));
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
	}
	
	@Override
	public List<Product> listProductByCategoryAndLimit(@Param("id_category")Long id, @Param("limit") int limit) {
		// TODO Auto-generated method stub
		return productRepository.listProductByCategoryAndLimit(id, limit);
	}
	
	@Override
	public Page<Product> pageListProductByCategory(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.getListProductByCategory(id, pageable);
	}
	
	@Override
	public Product getByCategoryTitle(String title) {
		// TODO Auto-generated method stub
		return productRepository.findByProductTitleEquals(title);
	}
	
	@Override
	public Page<Product> pageListByName(String title, Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.findByProductNameContaining(title, pageable);
	}
	
	@Override
	public List<Product> findByName(String name) {
		// TODO Auto-generated method stub
		return productRepository.findByProductNameContaining(name);
	}
	@Override
	public Long countProduct() {
		// TODO Auto-generated method stub
		return productRepository.count();
	}
	
	@Override
	public Long countProductSold() {
		// TODO Auto-generated method stub
		return productRepository.countProductSold();
	}
	
	@Override
	public Page<Product> pageTopListProductSold(Pageable pageable) {
		// TODO Auto-generated method stub
		return productRepository.pageTopListProductSold(pageable);
	}
}
