package com.tat.shoza.controller.admin;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.model.SetUp;
import com.tat.shoza.model.Slide;
import com.tat.shoza.service.SetUpService;
import com.tat.shoza.service.SlideService;

@Controller
public class WebInfoController {
	
	@Autowired
	private SlideService slideService;

	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private SetUpService setUpService;
	
	@Autowired
	private AdminInfoHelper adminInfoHelper;
	
	@GetMapping(value = "/admin/web-info")
	public String webInfo(Model model, Authentication authentication) {
		adminInfoHelper.dataAdminLayout("Thông Tin Web","admin/web_info", "web-info", authentication, model);
		model.addAttribute("slides", slideService.list());
		SetUp index_product_below_category = setUpService.findValueByName("index_product_below_category");
		model.addAttribute("index_product_below_category", index_product_below_category.getSetValue());
		SetUp product_by_category = setUpService.findValueByName("product_by_category");
		model.addAttribute("product_by_category", product_by_category.getSetValue());
		SetUp shoza_hotline = setUpService.findValueByName("shoza_hotline");
		model.addAttribute("shoza_hotline", shoza_hotline.getSetValue());
		SetUp contact_email = setUpService.findValueByName("contact_email");
		model.addAttribute("contact_email", contact_email.getSetValue());
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/admin/web-info/product-below-category")
	public String indexProductBelowCategory(@RequestParam(name = "index_product_below_category") String index_product_below_category) {   
		try {
			SetUp setUp = setUpService.findValueByName("index_product_below_category");
			setUp.setSetValue(index_product_below_category);
			setUpService.save(setUp);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/web-info";
	}
	
	@PostMapping(value = "/admin/web-info/product-by-category")
	public String productByCategory(@RequestParam(name = "product_by_category") String product_by_category) {   
		try {
			SetUp setUp = setUpService.findValueByName("product_by_category");
			setUp.setSetValue(product_by_category);
			setUpService.save(setUp);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/web-info";
	}
	
	@PostMapping(value = "/admin/web-info/shoza-hotline")
	public String hotline(@RequestParam(name = "shoza_hotline") String shoza_hotline) {   
		try {
			SetUp setUp = setUpService.findValueByName("shoza_hotline");
			setUp.setSetValue(shoza_hotline);
			setUpService.save(setUp);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/web-info";
	}
	
	@PostMapping(value = "/admin/web-info/contact-email")
	public String contactEmail(@RequestParam(name = "contact_email") String contact_email) {   
		try {
			SetUp setUp = setUpService.findValueByName("contact_email");
			setUp.setSetValue(contact_email);
			setUpService.save(setUp);
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println(e.getMessage());
		}
		return "redirect:/admin/web-info";
	}
	
	@GetMapping(value = "/admin/web-info/article/view-introduce")
	public String viewIntroduce(Model model, Authentication authentication) {
		adminInfoHelper.dataAdminLayout("Thông Tin - Giới Thiệu","admin/article_introduce_view", "web-info", authentication, model);
		model.addAttribute("article_introduce", setUpService.findValueByName("article_introduce").getSetValue());
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
	@GetMapping(value = "/admin/web-info/article/edit-introduce")
	public String editIntroduce(Model model, Authentication authentication) {
		adminInfoHelper.dataAdminLayout("Thông Tin - Giới Thiệu","admin/article_introduce_edit", "web-info", authentication, model);
		model.addAttribute("article_introduce", setUpService.findValueByName("article_introduce").getSetValue());
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
	@PostMapping(value = "/admin/web-info/article/introduce")
	public String saveIntroduce(@RequestParam(name = "article_introduce")String article_introduce) {
		SetUp introduce = setUpService.findValueByName("article_introduce");
		introduce.setSetValue(article_introduce);
		setUpService.save(introduce);
		return "redirect:/admin/web-info/article/view-introduce";
	}
	
}
