package com.tat.shoza.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.dto.ItemDTO;
import com.tat.shoza.dto.OrderInfoDTO;
import com.tat.shoza.model.Cart;
import com.tat.shoza.model.CommentStatus;
import com.tat.shoza.model.Order;
import com.tat.shoza.model.OrderDetail;
import com.tat.shoza.model.Product;
import com.tat.shoza.model.Revenue;
import com.tat.shoza.model.User;
import com.tat.shoza.service.CartService;
import com.tat.shoza.service.CommentService;
import com.tat.shoza.service.CommentStatusService;
import com.tat.shoza.service.OrderDetailService;
import com.tat.shoza.service.OrderService;
import com.tat.shoza.service.PaymentService;
import com.tat.shoza.service.ProductService;
import com.tat.shoza.service.RevenueService;

@Controller
@RequestMapping(value = "/pay/result")
public class PaymentResultController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private CommentStatusService commentStatusService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private RevenueService revenueService;
	
	@GetMapping
	public String resultPayment(HttpServletRequest request, Authentication authentication) {
		// trang thai giao dich
		String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
		System.err.println("vnp_TransactionStatus: " + vnp_TransactionStatus);
		// tong tien
		String vnp_Amount = request.getParameter("vnp_Amount");
		System.err.println("vnp_Amount: " + vnp_Amount);
		// ma don hang
		String vnp_TxnRef = request.getParameter("vnp_TxnRef");
		System.err.println("vnp_TxnRef: " + vnp_TxnRef);
		// ma ngan hang
		String vnp_BankCode = request.getParameter("vnp_BankCode");
		System.err.println("vnp_BankCode: " + vnp_BankCode);
		
		User user = userHelper.getUserAuthen(authentication);
		
		if(vnp_TransactionStatus.equals("00")) {
			// thanh cong
			ItemDTO[] itemDTO = BASE_METHOD.getItemDTOs();
			OrderInfoDTO[] infoDTO = BASE_METHOD.getInfoDTOs();
			
			Long totalPrice = (long) 0 ;
			int totalItem = 0;
			List<Product> list = new ArrayList<>();
			for(ItemDTO i: itemDTO) {
				Long nPrice = i.getPrice() * i.getQuantity();
				totalPrice += nPrice;
				totalItem += i.getQuantity();
				Product product = new Product();
				product.setId(i.getProduct());
				list.add(product);
			}

			Order order = new Order();
			Long savedId = null;
			// new oder
			if(infoDTO.length == 1) {
				order.setOrderUserName(infoDTO[0].getName());
				order.setOrderPhone(infoDTO[0].getPhone());
				order.setOrderAddess(infoDTO[0].getAddress());
				order.setOrderTotalItem(totalItem);
				order.setOrderTotalPrice(totalPrice);
				order.setUser(user);
				order.setOrderCode(vnp_TxnRef);
				order.setPayment(paymentService.get((long)2));
				order.setBankCode(vnp_BankCode);
				order.setOrderStatus(5);
				savedId = orderService.saveAndGetId(order);
			}
			System.err.println("_____________________");
			// delete product in cart
			for(Product product : list) {
//				System.err.println(product.getId());
				Cart cart = cartService.checkProductInUserCart(user.getId(), product.getId());
				Product delete = productService.getById(product.getId());
				cart.setProduct(delete);
				cart.setUser(user);
				
				CommentStatus commentStatus = new CommentStatus();
				commentStatus.setProduct(delete);
				commentStatus.setUser(user);
				commentStatus.setStatus(0);
				commentStatusService.save(commentStatus);
				
				cartService.delete(cart.getId());
			}
			
			// insert to order detail
			for(ItemDTO i: itemDTO) {
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProduct(productService.getById(i.getProduct()));
				orderDetail.setOrder(orderService.getById(savedId));
				orderDetail.setTotalPrice(i.getPrice());
				orderDetail.setQuantity(i.getQuantity());
				orderDetailService.save(orderDetail);
			}
			
			Revenue revenue = new Revenue();
			revenue.setOrder(orderService.getById(savedId));
			revenue.setTotalPrice(totalPrice);
			revenueService.save(revenue);
		}
		
		return "redirect:/shoza/my-cart?check-out-payment-success="+vnp_TxnRef;
	}

}
