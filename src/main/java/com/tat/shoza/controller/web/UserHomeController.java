package com.tat.shoza.controller.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.CartHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.component.WebInfoHelper;
import com.tat.shoza.model.Order;
import com.tat.shoza.model.OrderDetail;
import com.tat.shoza.model.Role;
import com.tat.shoza.model.User;
import com.tat.shoza.service.OrderDetailService;
import com.tat.shoza.service.OrderService;
import com.tat.shoza.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserHomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;

	@Autowired
	private CartHelper cartHelper;
	
	@Autowired
	private WebInfoHelper infoHelper;
	
	@GetMapping(value = {"","/","/profile"})
	public String profilePage(Model model, Authentication authentication, @ModelAttribute("mess") String mess) {
		 infoHelper.dataWebLayout("web/profile", model, authentication);
		 model.addAttribute("mess", null);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@PostMapping(value = "/profile")
	public String profileU(Model model, Authentication authentication, @ModelAttribute(name = "user")User u,
			@RequestParam(name = "pass")String pass, RedirectAttributes redirectAttributes) {
		 if(authentication != null) {
				User user = userHelper.getUserAuthen(authentication);
				model.addAttribute("user", user);
				model.addAttribute("totalQuantity", cartHelper.totalQuantityCartItemByUser(user.getId()));
				model.addAttribute("listCartByUser", cartHelper.listCartByUser(user.getId()));
				
				System.err.println(user.toString());
				
				if(pass.trim().length() == 0) {
					System.err.println("Pass trống");
				}else {
					user.setPasswords(encoder.encode(pass));
				}
				
				Boolean check = userService.checkUserEmail(u.getEmail(), user.getId());
				if(check) {
					// exists
					model.addAttribute("mess", "Hãy chọn 1 email hoặc số điện thoại khác");
				}else {
					user.setEmail(u.getEmail());
					user.setUserName(u.getUserName());
					userService.saveUser(user);
					model.addAttribute("mess", "Cập nhật tài khoản thành công");
				}
				
		 }
		 model.addAttribute("content", "web/profile");
		 
		
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/bought")
	public String bought(Model model, Authentication authentication) {
		
		if(authentication != null) {
			User user = userHelper.getUserAuthen(authentication);
			model.addAttribute("user", user);
			model.addAttribute("totalQuantity", cartHelper.totalQuantityCartItemByUser(user.getId()));
			model.addAttribute("listCartByUser", cartHelper.listCartByUser(user.getId()));
			
			List<Order> order = orderService.getOrderSuccessByUser(user.getId());
			List<OrderDetail> orderDetails = new ArrayList<>();
			
			for (Order o : order) {
			    List<OrderDetail> orderDetail = orderDetailService.listOrderDetailByOrder(o.getId());
			    orderDetails.addAll(orderDetail);
			}
			
			model.addAttribute("size", orderDetails.size());
			model.addAttribute("lisOrder", orderDetails);
			
	 }
		
		model.addAttribute("content", "web/bought");
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/order-wait")
	public String orderWait(Model model, Authentication authentication) {
		infoHelper.dataWebLayout("web/order_wait", model, authentication);
		User user = userHelper.getUserAuthen(authentication);
		List<Order> order = orderService.getOrderWaitByUser(user.getId());
		List<OrderDetail> orderDetails = new ArrayList<>();
		
		for (Order o : order) {
		    List<OrderDetail> orderDetail = orderDetailService.listOrderDetailByOrder(o.getId());
		    orderDetails.addAll(orderDetail);
		}
		model.addAttribute("size", orderDetails.size());
		model.addAttribute("lisOrder", orderDetails);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/order-cancel")
	public String orderCancel(Model model, Authentication authentication) {
		infoHelper.dataWebLayout("web/order_cancel", model, authentication);
		User user = userHelper.getUserAuthen(authentication);
		List<Order> order = orderService.getOrderCancelByUser(user.getId());
		List<OrderDetail> orderDetails = new ArrayList<>();
		
		for (Order o : order) {
		    List<OrderDetail> orderDetail = orderDetailService.listOrderDetailByOrder(o.getId());
		    orderDetails.addAll(orderDetail);
		}
		model.addAttribute("size", orderDetails.size());
		model.addAttribute("lisOrder", orderDetails);
		return BASE_FIELD.WEB_LAYOUT;
	}
}
