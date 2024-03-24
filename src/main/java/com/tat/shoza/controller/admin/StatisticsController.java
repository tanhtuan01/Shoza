package com.tat.shoza.controller.admin;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.dto.ProductDTO;
import com.tat.shoza.dto.ProductSoldDTO;
import com.tat.shoza.model.Order;
import com.tat.shoza.model.OrderDetail;
import com.tat.shoza.model.Product;
import com.tat.shoza.service.OrderDetailService;
import com.tat.shoza.service.OrderService;
import com.tat.shoza.service.ProductService;
import com.tat.shoza.service.RevenueService;

@Controller
@RequestMapping(value = "/admin/statistics")
public class StatisticsController {

	@Autowired
	private AdminInfoHelper adminInfoHelper;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RevenueService revenueService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	
	
	@GetMapping
	public String statisticsPage(Authentication authentication, Model model,
			@RequestParam(name = "pageOrder", defaultValue = "1")int page,
			@RequestParam(name = "sizeOrder", defaultValue = "10")int size,
			@RequestParam(name = "act", defaultValue = "order")String act,
			@RequestParam(name = "pageProduct", defaultValue = "1")int pageP,
			@RequestParam(name = "sizeProduct", defaultValue = "4")int sizeP,
			@RequestParam(name = "ptp", defaultValue = "1")int pageTopProduct,
			@RequestParam(name = "sizeTopProduct", defaultValue = "8")int sizeTopProduct,
			@RequestParam(name = "pof", defaultValue = "1")int pof,
			@RequestParam(name = "sof", defaultValue = "4")int sof) {
		adminInfoHelper.dataAdminLayout("Thống Kê","admin/statistics", "statistics", authentication, model);
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Pageable pageableProduct = PageRequest.of(pageP - 1, sizeP);
		LocalDateTime localDateTime = LocalDateTime.now();
		int day = localDateTime.getDayOfMonth();
		int month = localDateTime.getMonthValue();
		int year = localDateTime.getYear();
		
		Page<Order> pageOrder = orderService.pageListOrderSuccessDate(day, month, year, pageable);
		model.addAttribute("currentPageOrder", pageOrder.getNumber());
		model.addAttribute("totalPagesOrder", pageOrder.getTotalPages());
		model.addAttribute("pageOrder", page);
		model.addAttribute("act", act);
		model.addAttribute("orders", pageOrder);
		

		Long totalPriceToday = orderService.orderTotalPriceSuccessToday(day, month, year);
		model.addAttribute("price", totalPriceToday);
		model.addAttribute("sumrevenue", revenueService.sumRevenue());
		model.addAttribute("cproductsold", productService.countProductSold());
		
		Page<OrderDetail> pageOrderDetail = orderDetailService.pageListProductSoldToday(day, month, year, pageableProduct);
		
		int quantity = 0;
		for(OrderDetail orderDetail : pageOrderDetail) {
			quantity += orderDetail.getQuantity();
		}
		model.addAttribute("cproductsoldtoday", quantity);
		
		model.addAttribute("totalPagesProduct", pageOrderDetail.getTotalPages());
		model.addAttribute("pageProduct", pageP);
		model.addAttribute("orderDetails", pageOrderDetail);
		
		
		Pageable topProduct = PageRequest.of(pageTopProduct - 1, sizeTopProduct);
		Page<Product> pageTopProductSold = productService.pageTopListProductSold(topProduct);
		model.addAttribute("totalTopProduct", pageTopProductSold.getTotalPages());
		model.addAttribute("ptp", pageTopProduct);
		model.addAttribute("topProduct", pageTopProductSold);
		
		Pageable pageOrderFail = PageRequest.of(pof - 1, sof);
		Page<Order> pageListOrderFail = orderService.pageListOrderFailDate(day, month, year, pageOrderFail);
		model.addAttribute("totalOrderFail", pageListOrderFail.getTotalPages());
		model.addAttribute("pof", pof);
		model.addAttribute("ordersFail", pageListOrderFail);
		
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
//	@GetMapping
//	public String statisticsPage(Authentication authentication, Model model,
//			@RequestParam(name = "pageOrder", defaultValue = "1")int page,
//			@RequestParam(name = "sizeOrder", defaultValue = "10")int size,
//			@RequestParam(name = "act", defaultValue = "order")String act,
//			@RequestParam(name = "pageProduct", defaultValue = "1")int pageP,
//			@RequestParam(name = "sizeProduct", defaultValue = "1")int sizeP) {
//		adminInfoHelper.dataAdminLayout("Thống Kê","admin/statistics", "statistics", authentication, model);
//		
//		Pageable pageable = PageRequest.of(page - 1, size);
//		
//		
//		LocalDateTime localDateTime = LocalDateTime.now();
//		int day = localDateTime.getDayOfMonth();
//		int month = localDateTime.getMonthValue();
//		int year = localDateTime.getYear();
//		
//		Page<Order> pageOrder = orderService.pageListOrderSuccessToday(day, month, year, pageable);
//		model.addAttribute("currentPageOrder", pageOrder.getNumber());
//		model.addAttribute("totalPagesOrder", pageOrder.getTotalPages());
//		model.addAttribute("pageOrder", page);
//		model.addAttribute("act", act);
//		model.addAttribute("orders", pageOrder);
//		
//		Long totalPriceToday = orderService.orderTotalPriceSuccessToday(day, month, year);
//		model.addAttribute("price", totalPriceToday);
//		model.addAttribute("sumrevenue", revenueService.sumRevenue());
//		model.addAttribute("cproductsold", productService.countProductSold());
//		
//		List<Product> listProducts = new ArrayList<>();
//		for(Order order : pageOrder) {
//			List<OrderDetail> list = orderDetailService.listOrderDetailByOrder(order.getId());
//			
//			for(OrderDetail orderDetail : list) {
//				listProducts.add(orderDetail.getProduct());
//			}
//		}
//		
//		
//		Map<Product, Integer> mapProduct = new HashMap<>();
//		
//		for(Product product : listProducts) {
//			int count = mapProduct.getOrDefault(product, 0);
//			mapProduct.put(product, count + 1);
//		}
//		
//		Pageable pageProduct = PageRequest.of(pageP - 1, sizeP);
//		List<ProductSoldDTO> productSoldDTOs = new ArrayList<>();
//		for(Map.Entry<Product, Integer> mEntry: mapProduct.entrySet()) {
////			System.err.println(mEntry.getKey().getProductName());
////			System.err.println(mEntry.getValue());
//			ProductDTO productDTO = new ProductDTO();
//			productDTO.setProductName(mEntry.getKey().getProductName());
//			productDTO.setId(mEntry.getKey().getId());
//			productDTO.setProductImage(mEntry.getKey().getProductImage());
//			
//			ProductSoldDTO productSoldDTO = new ProductSoldDTO();
//			productSoldDTO.setCount(mEntry.getValue());
//			productSoldDTO.setProductDTO(productDTO);
//			
//			productSoldDTOs.add(productSoldDTO);
//		}
//
//		Page<ProductSoldDTO> pageProducts = new PageImpl<>(productSoldDTOs, pageProduct, productSoldDTOs.size() );
//		model.addAttribute("currentPageProduct", pageProducts.getNumber());
//		model.addAttribute("totalPagesProduct", pageProducts.getTotalPages());
//		model.addAttribute("pageProduct", pageP);
//		model.addAttribute("products", pageProducts);
//		
//		System.err.println(pageProducts.getSize());
//		return BASE_FIELD.ADMIN_LAYOUT;
//	}
	
}
