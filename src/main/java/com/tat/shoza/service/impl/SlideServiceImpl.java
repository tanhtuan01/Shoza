package com.tat.shoza.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.Slide;
import com.tat.shoza.repository.SlideRepository;
import com.tat.shoza.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService{

	@Autowired
	private SlideRepository repository;
	
	@Override
	public List<Slide> list() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	@Override
	public Slide save(Slide slide) {
		// TODO Auto-generated method stub
		return repository.save(slide);
	}
	
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}
}
