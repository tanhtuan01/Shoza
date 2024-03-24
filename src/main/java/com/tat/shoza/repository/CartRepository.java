package com.tat.shoza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
	
	@Query("select c from Cart c where c.user.id = :id_user AND c.product.id = :id_product")
	Cart checkProductInUserCart(@Param("id_user") Long id_user, @Param("id_product") Long id_product);
	
	@Query("select SUM(c.quantity) from Cart c where c.user.id = :id_user")
	Integer totalQuantityCartItemByUser(@Param("id_user") Long id);
	
	@Query("select c from Cart c where c.user.id = :id_user")
	List<Cart> listCartByUser(@Param("id_user") Long id);
	
	@Query("select c.product.productQuantity from Cart c where c.id = :id_cart")
	Integer getProductQuantityFromCart(@Param("id_cart") Long id);
	
	@Query("select c.quantity from Cart c where c.user.id = :id_user AND c.product.id = :id_product")
	Integer getProductQuantityFromCartByUser(@Param("id_user")Long id_user, @Param("id_product")Long id_product);
	
}
