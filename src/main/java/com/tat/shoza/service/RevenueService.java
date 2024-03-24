package com.tat.shoza.service;

import com.tat.shoza.model.Revenue;

public interface RevenueService {

	Revenue save(Revenue revenue);
	
	Long sumRevenue();
}
