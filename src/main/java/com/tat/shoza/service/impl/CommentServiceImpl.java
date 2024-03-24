package com.tat.shoza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.Comment;
import com.tat.shoza.repository.CommentRepository;
import com.tat.shoza.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public Comment save(Comment comment) {
		// TODO Auto-generated method stub
		return commentRepository.save(comment);
	}
	
	@Override
	public Page<Comment> pageListCommentByProduct(@Param("id_product")Long id_product, Pageable pageable) {
		// TODO Auto-generated method stub
		return commentRepository.pageListCommentByProduct(id_product, pageable);
	}
	
}
