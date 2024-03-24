package com.tat.shoza.controller.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.CartHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.component.WebInfoHelper;
import com.tat.shoza.dto.CategoryDTO;
import com.tat.shoza.dto.ProductDTO;
import com.tat.shoza.model.Category;
import com.tat.shoza.model.Product;
import com.tat.shoza.model.User;
import com.tat.shoza.service.CategoryService;
import com.tat.shoza.service.ProductService;

@Controller
public class CategoryHomeController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserHelper authenticationHelper;
	
	@Autowired
	private CartHelper cartHelper;
	
	@Autowired
	private WebInfoHelper infoHelper;
	
	@GetMapping(value = "/shoza/category/{title}")
	public String categoryPage(Authentication authentication,Model model, 
			@PathVariable(name = "title")String title,
			@RequestParam(name = "size", defaultValue = "15") int size,
			@RequestParam(name = "page", defaultValue = "1")int page) {
		Pageable pageable = PageRequest.of(page -1, infoHelper.productByCategory());
		List<Category> categories = categoryService.list();
		List<CategoryDTO> listCategory = new ArrayList<>();
		for(Category category : categories) {
			CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getCategoryName(), category.getCategoryTitle());
			listCategory.add(categoryDTO);
		}
		model.addAttribute("listCategory", listCategory);
		Category category = categoryService.findByCategoryTitle(title);
		model.addAttribute("category", category);
		model.addAttribute("text", "");
//		List<Product> listProducts = productService.listProductByCategory(category.getId());
//		List<ProductDTO> listProductDTOs = new ArrayList<>();
//		for(Product product : listProducts) {
//			ProductDTO dto = new ProductDTO(product.getProductName(), product.getProductTitle(),
//					product.getProductOldPrice(), product.getProductDiscount(), product.getProductCurrentPrice(),
//					product.getProductImage());
//			listProductDTOs.add(dto);
//		}
		Page<Product> productPage = productService.pageListProductByCategory(category.getId(), pageable);
		infoHelper.dataWebLayout("web/list_product_by_category" ,model, authentication);
		
		model.addAttribute("listProduct", productPage.getContent());
		//model.addAttribute("listProduct", listProductDTOs);
		model.addAttribute("currentPage", productPage.getNumber());
	    model.addAttribute("totalPages", productPage.getTotalPages());
	    model.addAttribute("page", page);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
}
