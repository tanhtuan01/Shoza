package com.tat.shoza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.SetUp;
import com.tat.shoza.repository.SetUpRepository;
import com.tat.shoza.service.SetUpService;

@Service
public class SetUpServiceImpl implements SetUpService{

	@Autowired
	private SetUpRepository repository;
	
	@Override
	public SetUp findValueByName(String field) {
		// TODO Auto-generated method stub
		return repository.findBySetNameEquals(field);
	}
	
	@Override
	public SetUp save(SetUp setUp) {
		// TODO Auto-generated method stub
		return repository.save(setUp);
	}
}
