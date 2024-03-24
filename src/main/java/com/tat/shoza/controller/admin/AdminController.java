package com.tat.shoza.controller.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.model.Order;
import com.tat.shoza.model.Product;
import com.tat.shoza.service.OrderService;
import com.tat.shoza.service.ProductService;
import com.tat.shoza.service.RevenueService;
import com.tat.shoza.service.UserService;

@Controller
public class AdminController {

	@Autowired
	private UserHelper userHelper;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private RevenueService revenueService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private AdminInfoHelper adminInfoHelper;

	String sorts = "";
	int d, m, y = 0;
	@GetMapping(value = "/admin")
	public String adminHomePage(Model model, Authentication authentication,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "sort", defaultValue = "") String sort,
			@RequestParam(name = "day", defaultValue = "0") int day,
			@RequestParam(name = "month", defaultValue = "0") int month,
			@RequestParam(name = "year", defaultValue = "0") int year) {
		adminInfoHelper.dataAdminLayout("Trang Chủ", "admin/admin_home_content", "home", authentication, model);
		model.addAttribute("cuser", userService.countUser());
		model.addAttribute("cproduct", productService.countProduct());
		model.addAttribute("sumrevenue", revenueService.sumRevenue());
		model.addAttribute("cosuccess", orderService.countSuccessOrder());
		model.addAttribute("cofail", orderService.countFailOrder());

		if(sort.equals("")) {
			sort = sorts;
		}
		
		Pageable pageable = PageRequest.of(page - 1, size);

		Page<Order> orderPage = orderService.pageListOrderSuccess(pageable);
		if (sort.equals("date")) {
			orderPage = orderService.pageListByDate(day, month, year, pageable);
			sorts = "date";
			
		}else if(sort.equals("today")) { 
			LocalDateTime localDateTime = LocalDateTime.now();
			orderPage = orderService.pageListByDate(localDateTime.getDayOfMonth(), localDateTime.getMonthValue(), localDateTime.getYear(), pageable);
			System.err.println(localDateTime.getDayOfMonth() +"." + localDateTime.getMonthValue() +"."+ localDateTime.getYear());
		}else {
			sorts = "all";
		}
		model.addAttribute("order", orderPage.getContent());
		model.addAttribute("currentPage", orderPage.getNumber());
		model.addAttribute("totalPages", orderPage.getTotalPages());

		model.addAttribute("page", page);
		model.addAttribute("sort", sort);
		model.addAttribute("d", day);model.addAttribute("m", month);model.addAttribute("y", year);
		
		return BASE_FIELD.ADMIN_LAYOUT;
	}

	@GetMapping(value = { "/admin/search" })
	public String productListPage(Model model, Authentication authentication,
			@RequestParam(name = "product") String product) {
		List<Product> list = productService.findByName(product);
		model.addAttribute("list", list);
		adminInfoHelper.dataAdminLayout("Trang Chủ", "admin/list_product", "list-product", authentication, model);
		return BASE_FIELD.ADMIN_LAYOUT;
	}

}
