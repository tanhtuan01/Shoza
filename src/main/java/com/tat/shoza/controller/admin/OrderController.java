package com.tat.shoza.controller.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.dto.OrderDetailDTO;
import com.tat.shoza.model.Category;
import com.tat.shoza.model.Order;
import com.tat.shoza.model.OrderDetail;
import com.tat.shoza.model.Product;
import com.tat.shoza.model.Revenue;
import com.tat.shoza.service.CategoryService;
import com.tat.shoza.service.OrderDetailService;
import com.tat.shoza.service.OrderService;
import com.tat.shoza.service.ProductService;
import com.tat.shoza.service.RevenueService;

@Controller
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private AdminInfoHelper adminInfoHelper;
	
	String orderSTT = "";
	
	@GetMapping(value = "/admin/order")
	public String orderList(Model model, @RequestParam(name = "size", defaultValue = "10")int size,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "stt", defaultValue = "") String stt,
			Authentication authentication,
			@RequestParam(name = "day", defaultValue = "1")int d,
			@RequestParam(name = "month", defaultValue = "2")int m,
			@RequestParam(name = "year", defaultValue = "3")int y) {
		if(stt.equals("")) {
			stt = orderSTT;
		}

		Pageable pageable = PageRequest.of(page - 1, size);
		if(stt.equals("cxn")) {
			Page<Order> pageListOrderWithStatus = orderService.pageListOrderWithStatus(0, pageable) ;
			model.addAttribute("order", pageListOrderWithStatus.getContent());
			model.addAttribute("currentPage", pageListOrderWithStatus.getNumber());
			model.addAttribute("totalPages", pageListOrderWithStatus.getTotalPages());
			orderSTT = "cxn";
		}else if(stt.equals("tt")) {
			Page<Order> pageListOrderWithStatus = orderService.pageListOrderWithStatus(5, pageable) ;
			model.addAttribute("order", pageListOrderWithStatus.getContent());
			model.addAttribute("currentPage", pageListOrderWithStatus.getNumber());
			model.addAttribute("totalPages", pageListOrderWithStatus.getTotalPages());
			orderSTT = "tt";
		} else if(stt.equals("shipping")){
			Page<Order> pageListOrderWithStatus = orderService.pageListOrderWithStatus(2, pageable) ;
			model.addAttribute("order", pageListOrderWithStatus.getContent());
			model.addAttribute("currentPage", pageListOrderWithStatus.getNumber());
			model.addAttribute("totalPages", pageListOrderWithStatus.getTotalPages());
			orderSTT = "shipping";
		}else if(stt.equals("today")) {
			LocalDateTime localDateTime = LocalDateTime.now();
			int day = localDateTime.getDayOfMonth();
			int month = localDateTime.getMonthValue();
			int year = localDateTime.getYear();
			Page<Order> pageListOrderToday = orderService.pageListByDate(day, month, year, pageable);
			model.addAttribute("order", pageListOrderToday.getContent());
			model.addAttribute("currentPage", pageListOrderToday.getNumber());
			model.addAttribute("totalPages", pageListOrderToday.getTotalPages());
			orderSTT = "today";
		}else if(stt.equals("date")) {
			Page<Order> pageListOrderToday = orderService.pageListByDate(d, m, y, pageable);
			model.addAttribute("order", pageListOrderToday.getContent());
			model.addAttribute("currentPage", pageListOrderToday.getNumber());
			model.addAttribute("totalPages", pageListOrderToday.getTotalPages());
			orderSTT = "date";
		}
		else {
			if( stt == null || stt.equals("")) {
				Page<Order> orderPage = orderService.pageListOrder(pageable);
				model.addAttribute("order", orderPage.getContent());
				model.addAttribute("currentPage", orderPage.getNumber());
				model.addAttribute("totalPages", orderPage.getTotalPages());
			}
		}

		adminInfoHelper.dataAdminLayout("Đơn Đặt Hàng","admin/order", "order", authentication, model);
		model.addAttribute("page", page);
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
	@GetMapping(value = "/admin/listOrder")
	public String list() {
		orderSTT = "";
		return "redirect:/admin/order";
	}
	
	@GetMapping(value = "/admin/order/updateOrderStatus")
	public String updateOrderStatus(@RequestParam(name = "oid")Long id, @RequestParam(name = "ostt")int stt) {
		Order order = orderService.getById(id);
		order.setOrderStatus(stt);
		
		if(stt == 3) {
			List<OrderDetail> list = orderDetailService.listOrderDetailByOrder(id);
			
			for(OrderDetail orderDetail : list) {
				Product product = orderDetail.getProduct();
				Category category = categoryService.getCategoryByProductId(product.getId());
				product.setProductSold(orderDetail.getQuantity());
				product.setCategory(category);
				product.setProductQuantity(product.getProductQuantity() - orderDetail.getQuantity());
				productService.save(product);
			}
			
			Revenue revenue = new Revenue();
			revenue.setTotalPrice(order.getOrderTotalPrice());
			revenue.setOrder(order);
			revenueService.save(revenue);
			
			
			
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		String formattedDate = localDateTime.format(formatter);
		order.setOrderStatusAt(formattedDate);
		//
		orderService.save(order);
		return "redirect:/admin/listOrder";
		
	}
	
	@ResponseBody
	@GetMapping(value = "/admin/order-detail/order-detail-by")
	public List<OrderDetailDTO> list(@RequestParam(name = "id")Long id){
		List<OrderDetail> list = orderDetailService.listOrderDetailByOrder(id);
		List<OrderDetailDTO> orderDetailDTOs = new ArrayList<>();
		for(OrderDetail orderDetail : list) {
			OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
			orderDetailDTO.setProductName(orderDetail.getProduct().getProductName());
			orderDetailDTO.setImage(orderDetail.getProduct().getProductImage());
			orderDetailDTO.setQuantity(orderDetail.getQuantity());
			orderDetailDTO.setTotalPrice(orderDetail.getTotalPrice());
			orderDetailDTOs.add(orderDetailDTO);
		}
		return orderDetailDTOs;
	}
	
}
