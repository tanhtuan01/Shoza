package com.tat.shoza.controller.web;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.CartHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.component.WebInfoHelper;
import com.tat.shoza.dto.CategoryDTO;
import com.tat.shoza.dto.ProductDTO;
import com.tat.shoza.model.Category;
import com.tat.shoza.model.Feedback;
import com.tat.shoza.model.Product;
import com.tat.shoza.model.Slide;
import com.tat.shoza.model.User;
import com.tat.shoza.service.CartService;
import com.tat.shoza.service.CategoryService;
import com.tat.shoza.service.FeedbackService;
import com.tat.shoza.service.ProductService;
import com.tat.shoza.service.SetUpService;
import com.tat.shoza.service.SlideService;
import com.tat.shoza.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@Autowired
	private UserHelper authenticationHelper;
	
	@Autowired
	private CartHelper cartHelper;
	
	@Autowired
	private CartService cartService;

	@Autowired
	private SlideService slideService;
	
	@Autowired
	private WebInfoHelper infoHelper;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private SetUpService setUpService;
	
	LocalDateTime localDateTime = LocalDateTime.now();

	@GetMapping(value = { "/", "" })
	public String homePage(Model model, Authentication authentication) {

		infoHelper.dataWebLayout("web/home_content", model, authentication);
			
		
		List<Category> listCategories = categoryService.list();
		List<CategoryDTO> listCategoryDTOs = new ArrayList<>();
		List<Slide> slides = slideService.list();
		model.addAttribute("slides", slides);
		for (Category c : listCategories) {
			CategoryDTO categoryDTO = new CategoryDTO(c.getCategoryName(), c.getCategoryTitle(), c.getCategoryImage());
			listCategoryDTOs.add(categoryDTO);
		}
		model.addAttribute("listCategory", listCategoryDTOs);
		List<Category> category = categoryService.getCategoryShowInIndex();
		for (Category cc : category) {
			List<Product> products = productService.listProductByCategoryAndLimit(cc.getId(),
					infoHelper.productBelowCategory());
			cc.setProducts(products);
		}
		model.addAttribute("cProducts", category);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/shoza/search")
	public String search(@RequestParam(name = "product")String product, Model model,
			@RequestParam(name = "size", defaultValue = "15") int size,
			@RequestParam(name = "page", defaultValue = "1")int page, Authentication authentication) {
		Pageable pageable = PageRequest.of(page -1, size);
		List<Category> listCategory = categoryService.list();
		model.addAttribute("listCategory", listCategory);
		Page<Product> productPage = productService.pageListByName(product, pageable);
		model.addAttribute("listProduct", productPage.getContent());
		//model.addAttribute("listProduct", listProductDTOs);
		model.addAttribute("currentPage", productPage.getNumber());
	    model.addAttribute("totalPages", productPage.getTotalPages());
	    model.addAttribute("page", page);
	    model.addAttribute("text", "Kết quả tìm kiếm cho từ khóa: '"+product+"'");
	    model.addAttribute("product", product);
	    infoHelper.dataWebLayout("web/list_product_by_category", model, authentication);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/shoza/feedback")
	public String feedback(Model model, Authentication authentication) {
		infoHelper.dataWebLayout("page_feedback", model, authentication);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@ResponseBody
	@GetMapping(value = "/shoza/feedback/save")
	public String saveFeedBack(@RequestParam(name = "feedback-content")String content, Authentication authentication) {
		Feedback feedback = new Feedback();
		String replaceContent = content.replace("\"", "\\\"")
                .replace("'", "\\'")
                .replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\n", "\\n");
		feedback.setStatus(0);
		feedback.setContent(replaceContent);
		if(authentication != null) {
			User user = authenticationHelper.getUserAuthen(authentication);
			feedback.setUser(user);
		}
		feedback.setCreatedAt(localDateTime.now()+"");
		feedbackService.save(feedback);
		return "feedback";
	}
	
	@GetMapping(value = "/shoza/introduce")
	public String introduce(Model model, Authentication authentication) {
		infoHelper.dataWebLayout("page_introduce", model, authentication);
		model.addAttribute("article_introduce", setUpService.findValueByName("article_introduce").getSetValue());
		return BASE_FIELD.WEB_LAYOUT;
	}
}
