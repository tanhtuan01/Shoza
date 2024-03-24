package com.tat.shoza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.CommentStatus;

public interface CommentStatusRepository extends JpaRepository<CommentStatus, Long>{

	@Query("select c from CommentStatus c where c.user.id = :id_user AND c.product.id = :id_product and c.status = 0")
	List<CommentStatus> listCommentStatusByUserProduct(@Param("id_user")Long id_user, @Param("id_product")Long id_product);
	
}
