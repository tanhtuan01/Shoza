package com.tat.shoza.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tat.shoza.model.Cart;
import com.tat.shoza.service.CartService;

@Component
public class CartHelper {

	@Autowired
	private CartService cartService;
	
	public int totalQuantityCartItemByUser(Long id) {
		Integer total = cartService.totalQuantityCartItemByUser(id);
		return (total !=null) ? total : 0;
	}
	
	public List<Cart> listCartByUser(Long id){
		return cartService.listCartByUser(id);
	}
	
}
