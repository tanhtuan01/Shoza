package com.tat.shoza.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	@Query("select c from Comment c where c.product.id = :id_product")
	Page<Comment> pageListCommentByProduct(@Param("id_product")Long id_product, Pageable pageable);
	
}
