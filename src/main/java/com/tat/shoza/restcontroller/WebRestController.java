package com.tat.shoza.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tat.shoza.dto.ProductDTO;
import com.tat.shoza.model.Cart;
import com.tat.shoza.model.Product;
import com.tat.shoza.service.CartService;
import com.tat.shoza.service.ProductService;

@RestController
public class WebRestController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping(value = "/webs/cart-quantity")
	public Integer resetQuantityCart(@RequestParam(name = "id") Long id, @RequestParam(name = "q")int q) {
		int productQuantity = cartService.getProductQuantityFromCart(id);
		
		Cart cart = cartService.getById(id);
		
		if(cart.getQuantity() > productQuantity) {
			cart.setQuantity(productQuantity);
		}
		
		cartService.save(cart);
		return productQuantity;
	}
	
	@GetMapping(value = "/webs/up-quantity")
	public Integer upQuantity(@RequestParam(name = "id") Long id) {
		int productQuantity = cartService.getProductQuantityFromCart(id);
		Cart cart = cartService.getById(id);
		int q = cart.getQuantity();
		if(q + 1 <= productQuantity) {
			cart.setQuantity(q+1);
			cartService.save(cart);
			return q+1;
		}
		return q;
	}
	
	@GetMapping(value = "/webs/low-quantity")
	public Integer lowQuantity(@RequestParam(name = "id") Long id) {
		Cart cart = cartService.getById(id);
		int q = cart.getQuantity();
		if(q -1 >= 1) {
			cart.setQuantity(q-1);
			cartService.save(cart);
			return q-1;
		}
		return q;
	}
	
	@GetMapping(value = "/shoza/search-by")
	public List<ProductDTO> lists(@RequestParam(name = "product")String product){
		List<Product> list = productService.findByName(product);
		List<ProductDTO> list2 = new ArrayList<>();
		for(Product product2 : list) {
			ProductDTO productDTO = new ProductDTO(product2.getId(), product2.getProductName(), product2.getProductTitle(), product2.getProductImage());
			list2.add(productDTO);
		}
		return list2;
	}
	
}
