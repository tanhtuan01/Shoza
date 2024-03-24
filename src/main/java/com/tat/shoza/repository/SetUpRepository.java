package com.tat.shoza.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tat.shoza.model.SetUp;
import java.util.List;


public interface SetUpRepository extends JpaRepository<SetUp, Long>{
	
	SetUp findBySetNameEquals(String setName);
	
}
