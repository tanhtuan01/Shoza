package com.tat.shoza.controller.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.base.BASE_METHOD;
import com.tat.shoza.component.CartHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.component.WebInfoHelper;
import com.tat.shoza.dto.ItemDTO;
import com.tat.shoza.dto.OrderInfoDTO;
import com.tat.shoza.model.Cart;
import com.tat.shoza.model.CommentStatus;
import com.tat.shoza.model.Order;
import com.tat.shoza.model.OrderDetail;
import com.tat.shoza.model.Product;
import com.tat.shoza.model.User;
import com.tat.shoza.service.CartService;
import com.tat.shoza.service.CommentStatusService;
import com.tat.shoza.service.OrderDetailService;
import com.tat.shoza.service.OrderService;
import com.tat.shoza.service.PaymentService;
import com.tat.shoza.service.ProductService;

@Controller
public class CartHomeController {

	@Autowired
	private UserHelper authenticationHelper;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private CartHelper cartHelper;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private WebInfoHelper infoHelper;
	
	@Autowired
	private CommentStatusService commentStatusService;
	
	@Autowired
	private PaymentService paymentService;
	
	@PreAuthorize("isAuthenticated()")
	@ResponseBody
	@GetMapping(value = "/shoza/check/checkCartBeforeAdd")
	public boolean checkCartBeforeAdd(@RequestParam(name ="id_product")Long id_product,
			@RequestParam(name = "quantity")int quantity, Authentication authentication) {
		Product product = productService.getById(id_product);
		int productQuantity = product.getProductQuantity();
		User user = authenticationHelper.getUserAuthen(authentication);
		int productQuantityInCart = cartService.getProductQuantityFromCartByUser(user.getId(), id_product);
		if(productQuantity == productQuantityInCart) {
			return false;
		}else if(productQuantityInCart + quantity > productQuantity) {
			return false;
		}
		else if(productQuantityInCart + quantity <= productQuantity) {
			return true;
		}
		return false;
	}

	@GetMapping(value = "/shoza/addToCart")
	public String addToCart(Model model,@RequestParam(name = "p") Long id_product,
			@RequestParam(name = "q")int quantity, Authentication authentication,
			RedirectAttributes redirectAttributes) {
		
		User user = authenticationHelper.getUserAuthen(authentication);
		Cart checkUserProduct = cartService.checkProductInUserCart(user.getId(), id_product);
		Product product = productService.getById(id_product);
		if(checkUserProduct == null) {
			 Cart cart = new Cart(); 
			 cart.setUser(user); 
			 cart.setProduct(product);
			 cart.setQuantity(quantity); 
			 cartService.save(cart);
		}else {
			Cart cart = cartService.getById(checkUserProduct.getId());
			cart.setUser(user); 
			cart.setProduct(product);
			cart.setQuantity(quantity + checkUserProduct.getQuantity()); 
			cartService.save(cart);
		}
//		redirectAttributes.addFlashAttribute("shake", "shake");
		return "redirect:/shoza/product/" + product.getProductTitle();
	}
	
	@GetMapping(value = "/shoza/my-cart")
	public String myCart(Model model, Authentication authentication) {
		model.addAttribute("hide", "none");
		infoHelper.dataWebLayout("web/cart", model, authentication);
		return BASE_FIELD.WEB_LAYOUT;
	}
	
	@GetMapping(value = "/shoza/my-cart/delete/{id}")
	public String deleteCartItem(@PathVariable(name = "id") Long id) {
		cartService.delete(id);
		return "redirect:/shoza/my-cart";
	}
	
	@GetMapping(value = "/shoza/my-cart/checkout")
	public String p(@RequestParam(name = "items") String dataItem,
			@RequestParam(name = "data")String data, Authentication authentication) {
		//System.out.println(data);
		User user = authenticationHelper.getUserAuthen(authentication);
		Gson gson = new Gson();
		ItemDTO[] itemDTO = gson.fromJson(dataItem, ItemDTO[].class);
		OrderInfoDTO[] infoDTO = gson.fromJson(data, OrderInfoDTO[].class);
		Long totalPrice = (long) 0 ;
		int totalItem = 0;
		List<Product> list = new ArrayList<>();
		for(ItemDTO i: itemDTO) {
//			System.err.println(i.toString());
			Long nPrice = i.getPrice() * i.getQuantity();
			totalPrice += nPrice;
			totalItem += i.getQuantity();
			Product product = new Product();
			product.setId(i.getProduct());
			list.add(product);
		}
		
		//System.out.println(itemDTO.length);
		//System.err.println(infoDTO.length);
		/*
		 * for(OrderInfoDTO orderInfoDTO: infoDTO) {
		 * System.err.println(orderInfoDTO.toString()); Order order = new Order(); }
		 */
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
			order.setOrderCode(BASE_METHOD.randomOrderCode() + user.getId());
			order.setPayment(paymentService.get((long)1));
			order.setBankCode("NO_BANK");
			savedId = orderService.saveAndGetId(order);
		}
		System.err.println("_____________________");
		// delete product in cart
		for(Product product : list) {
//			System.err.println(product.getId());
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
		return "redirect:/shoza/my-cart";
	}
}
