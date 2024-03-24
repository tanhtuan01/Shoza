package com.tat.shoza.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tat.shoza.model.Revenue;
import com.tat.shoza.repository.RevenueRepository;
import com.tat.shoza.service.RevenueService;

@Service
public class RevenueServiceImpl implements RevenueService{

	@Autowired
	private RevenueRepository revenueRepository;
	
	@Override
	public Revenue save(Revenue revenue) {
		// TODO Auto-generated method stub
		return revenueRepository.save(revenue);
	}
	
	@Override
	public Long sumRevenue() {
		// TODO Auto-generated method stub
		return revenueRepository.sumRevenue();
	}
	
}
