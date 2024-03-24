package com.tat.shoza.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

@Component
public class AdminInfoHelper {

	@Autowired
	private UserHelper userHelper;
	
	public void dataAdminLayout(String title, String layout, String active, Authentication authentication, Model model) {
		model.addAttribute("title", title);
		model.addAttribute("content", layout);
		model.addAttribute("active", active);
		model.addAttribute("user", userHelper.getUserAuthen(authentication));
	}
	
}
