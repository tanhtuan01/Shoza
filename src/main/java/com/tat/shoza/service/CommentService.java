package com.tat.shoza.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tat.shoza.model.Comment;

public interface CommentService {

	Comment save(Comment comment);
	
	Page<Comment> pageListCommentByProduct(Long id_product, Pageable pageable);
}
