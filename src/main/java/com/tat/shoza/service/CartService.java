package com.tat.shoza.service;

import java.util.List;

import com.tat.shoza.model.Cart;

public interface CartService {

	Cart checkProductInUserCart(Long id_user, Long id_product);
	
	Cart save(Cart cart);
	
	Cart getById(Long id);
	
	Integer totalQuantityCartItemByUser(Long id);
	
	List<Cart> listCartByUser(Long id);
	
	void delete(Long id);
	
	Integer getProductQuantityFromCart(Long id);
	
	Integer getProductQuantityFromCartByUser(Long id_user, Long id_product);
}
