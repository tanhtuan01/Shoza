package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.CommentStatus;
import com.tat.shoza.repository.CommentStatusRepository;
import com.tat.shoza.service.CommentStatusService;

@Service
public class CommentStatusServiceImpl implements CommentStatusService{

	@Autowired
	private CommentStatusRepository commentStatusRepository;
	
	@Override
	public CommentStatus save(CommentStatus commentStatus) {
		// TODO Auto-generated method stub
		return commentStatusRepository.save(commentStatus);
	}
	
	@Override
	public List<CommentStatus> listCommentStatusByUserProduct(@Param("id_user")Long id_user, @Param("id_product")Long id_product) {
		// TODO Auto-generated method stub
		return commentStatusRepository.listCommentStatusByUserProduct(id_user, id_product);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		commentStatusRepository.deleteById(id);;
	}
	
	@Override
	public CommentStatus get(Long id) {
		// TODO Auto-generated method stub
		return commentStatusRepository.getById(id);
	}
}
