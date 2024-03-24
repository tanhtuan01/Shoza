package com.tat.shoza.service;

import com.tat.shoza.model.SetUp;

public interface SetUpService {

	SetUp findValueByName(String field);
	
	SetUp save(SetUp setUp);
}
