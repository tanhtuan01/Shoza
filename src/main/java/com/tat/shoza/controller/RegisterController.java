package com.tat.shoza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tat.shoza.dto.UserDTO;
import com.tat.shoza.service.UserService;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@GetMapping
	public String registerPage(Model model) {
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		return "page_register";
	}
	
	@PostMapping
	public String register(@ModelAttribute(name = "user")UserDTO userDTO) {
		boolean check = userService.checkUserEmail(userDTO.getEmail(), (long) 0);
		if(check) {
			return "redirect:/register?error";
		}else {
			userService.save(userDTO);
			return "redirect:/register?success";
		}
		
	}
}
