package com.tat.shoza.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tat.shoza.service.CategoryService;
import com.tat.shoza.service.ProductService;

@RestController
@RequestMapping(value = "/api-admin")
public class AdminRestController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/checkCategoryTitle")
	public boolean checkCategoryTitle(@RequestParam(name = "title")String title, @RequestParam(name = "id")Long id) {
		return categoryService.existsByCategoryTitle(title, id);
	}
	
	@GetMapping(value = "/checkProductTitle")
	public boolean checkProductTitle(@RequestParam(name = "title")String title, @RequestParam(name = "id")Long id) {
		return productService.existsByProductTitle(title, id);
	}
	
}
