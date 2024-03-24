package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.ListProductImage;
import com.tat.shoza.repository.ListProductImageRepository;
import com.tat.shoza.service.ListProductImageService;

@Service
public class ListProductImageServiceImpl implements ListProductImageService{

	@Autowired
	private ListProductImageRepository productImageRepository;
	
	@Override
	public ListProductImage save(ListProductImage listProductImage) {
		// TODO Auto-generated method stub
		return productImageRepository.save(listProductImage);
	}
	
	@Override
	public List<ListProductImage> listByProductId(@Param("id_product")Long id) {
		// TODO Auto-generated method stub
		return productImageRepository.listByProductId(id);
	}
	
}
