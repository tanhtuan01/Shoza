package com.tat.shoza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tat.shoza.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByEmail(String email);
	

	boolean existsByEmailEquals(String email);
	
	boolean existsByEmailEqualsAndIdNot(String email, Long id);
	
	//Integer countById();
}
