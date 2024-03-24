package com.tat.shoza.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tat.shoza.model.Cart;
import com.tat.shoza.model.User;
import com.tat.shoza.service.UserService;

@Component
public class UserHelper {

	@Autowired
	private UserService userService;
	
	public User getUserAuthen(Authentication authentication) {
		if(authentication != null) {
			String email = authentication.getName();
			User user = userService.getByEmail(email);
			return user;
		}else {
			return null;
		}
		
	}
	
	
	
}
