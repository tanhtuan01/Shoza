package com.tat.shoza.controller.admin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.dto.CategoryDTO;
import com.tat.shoza.dto.ProductDTO;
import com.tat.shoza.model.Category;
import com.tat.shoza.model.Product;
import com.tat.shoza.service.CategoryService;
import com.tat.shoza.service.ProductService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private UserHelper userHelper;
	
	private ModelMapper mapper = new ModelMapper();
	
	@Autowired
	private AdminInfoHelper adminInfoHelper;
	
//
	@GetMapping(value = "/admin/category")
	public String categoryPage(Model model, Authentication authentication) {
		adminInfoHelper.dataAdminLayout("Danh Mục","admin/category", "category", authentication, model);
		model.addAttribute("category", new CategoryDTO());
		model.addAttribute("list", categoryService.list());
		//model.addAttribute("notify", categorymessage);, @ModelAttribute(name = "notify") String categorymessage
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@PostMapping(value = "/admin/category/save")
	public String saveCategory(RedirectAttributes redirectAttributes,
			@ModelAttribute(name = "category") CategoryDTO categoryDTO,
			@RequestParam(name = "categoryIMG") MultipartFile categoryIMG) {
		
		Category category = new Category();
		

		if(categoryDTO.getId() != null) {
			category = categoryService.get(categoryDTO.getId());
//			List<Product> list = productService.listProductByCategory(categoryDTO.getId());
//			category.setProducts(list);
			
		}
		category.setCategoryName(categoryDTO.getCategoryName());
		category.setCategoryTitle(categoryDTO.getCategoryTitle());
		category.setCategoryStatus(categoryDTO.getCategoryStatus());
		if(categoryIMG != null && !categoryIMG.isEmpty()) {
			System.out.println("CÓ ẢNH");
			String imageName = BASE_METHOD.randomImgName();
			String imagePath = BASE_METHOD.categoryPathUploadImg(imageName);
			try {
				Files.write(Paths.get(imagePath), categoryIMG.getBytes());
				category.setCategoryImage(imageName);
				redirectAttributes.addAttribute("categorymessage","category-success");
				categoryService.save(category);
			} catch (Exception e) {
				// TODO: handle exception
				System.err.println("=> LỖI THÊM ẢNH");
			}
		}else {
			redirectAttributes.addAttribute("notify","category-success");
			System.out.println("KHÔNG ẢNH");
			categoryService.save(category);
		}
		System.err.println(category.toString());
		return "redirect:/admin/category";
	}
	
	@GetMapping(value = "/admin/category/delete/{id}")
	public String deleteCategory(@PathVariable(name = "id")Long id, RedirectAttributes redirectAttributes) {
		categoryService.delete(id);
		redirectAttributes.addAttribute("notify","category-delsuccess");
		return "redirect:/admin/category";
	}

}
