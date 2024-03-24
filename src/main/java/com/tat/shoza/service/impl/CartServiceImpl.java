package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.Cart;
import com.tat.shoza.repository.CartRepository;
import com.tat.shoza.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Override
	public Cart checkProductInUserCart(@Param("id_user") Long id_user, @Param("id_product") Long id_product) {
		// TODO Auto-generated method stub
		return cartRepository.checkProductInUserCart(id_user, id_product);
	}

	@Override
	public Cart save(Cart cart) {
		// TODO Auto-generated method stub
		return cartRepository.save(cart);
	}

	@Override
	public Cart getById(Long id) {
		// TODO Auto-generated method stub
		return cartRepository.getById(id);
	}

	@Override
	public Integer totalQuantityCartItemByUser(@Param("id_user") Long id) {
		// TODO Auto-generated method stub
		return cartRepository.totalQuantityCartItemByUser(id);
	}

	@Override
	public List<Cart> listCartByUser(@Param("id_user") Long id) {
		// TODO Auto-generated method stub
		return cartRepository.listCartByUser(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		cartRepository.deleteById(id);
	}

	@Override
	public Integer getProductQuantityFromCart(@Param("id_cart")Long id) {
		// TODO Auto-generated method stub
		return cartRepository.getProductQuantityFromCart(id);
	}
	
	@Override
	public Integer getProductQuantityFromCartByUser(@Param("id_user")Long id_user, @Param("id_product")Long id_product) {
		// TODO Auto-generated method stub
		return cartRepository.getProductQuantityFromCartByUser(id_user, id_product);
	}
}
