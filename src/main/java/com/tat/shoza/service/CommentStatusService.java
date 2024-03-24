package com.tat.shoza.service;

import java.util.List;

import com.tat.shoza.model.CommentStatus;

public interface CommentStatusService {

	CommentStatus save(CommentStatus commentStatus);
	
	List<CommentStatus> listCommentStatusByUserProduct(Long id_user, Long id_product);
	
	void delete(Long id);
	
	CommentStatus get(Long id);
}
