package com.tat.shoza.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.tat.shoza.dto.UserDTO;
import com.tat.shoza.model.User;

public interface UserService extends UserDetailsService{

	User save(UserDTO userDTO);
	
	User getByEmail(String email);

	List<User> list();
	
	User getById(Long id);
	
	User saveUser(User user);
	
	boolean checkUserEmail(String email, Long id);
	
	Long countUser();
	
	User saveAdmin(UserDTO userDTO);
}
