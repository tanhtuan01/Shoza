package com.tat.shoza.service;

import java.util.List;

import com.tat.shoza.model.ListProductImage;

public interface ListProductImageService {

	ListProductImage save(ListProductImage listProductImage);
	
	List<ListProductImage> listByProductId(Long id);
}
