package com.tat.shoza.service;

import java.util.List;

import com.tat.shoza.model.Slide;

public interface SlideService {

	List<Slide> list();

	Slide save(Slide slide);
	
	void delete(Long id);
}
