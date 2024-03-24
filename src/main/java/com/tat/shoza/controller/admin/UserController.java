package com.tat.shoza.controller.admin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tat.shoza.base.BASE_FIELD;
import com.tat.shoza.component.AdminInfoHelper;
import com.tat.shoza.component.UserHelper;
import com.tat.shoza.dto.UserDTO;
import com.tat.shoza.dto.UserRoleDTO;
import com.tat.shoza.model.Role;
import com.tat.shoza.model.User;
import com.tat.shoza.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserHelper userHelper;
	
	@Autowired
	private AdminInfoHelper adminInfoHelper;
	
	
	@GetMapping(value = "/admin/users")
	public String usersPage(Model model, Authentication authentication) {
		adminInfoHelper.dataAdminLayout("Người Dùng","admin/users", "users", authentication, model);
		List<User> list = userService.list();
		List<UserRoleDTO> listUserRoleDTOs = new ArrayList<>();
		
		for(User user : list) {
			String userName = user.getUserName();
			String email = user.getEmail();
			Long userId = user.getId();
			for(Role role : user.getRoles()) {
				String roleName = role.getRoleName();
				UserRoleDTO userRoleDTO = new UserRoleDTO(userId, userName, email, roleName);
				listUserRoleDTOs.add(userRoleDTO);
			}
			
		}
		model.addAttribute("list", listUserRoleDTOs);
		return BASE_FIELD.ADMIN_LAYOUT;
	}
	
	@ResponseBody
	@GetMapping(value = "/admin/users/updateRole")
	public void updateRole(@RequestParam(name = "id")Long id,
			@RequestParam(name = "role")String role) {
		User user = userService.getById(id);
		user.setUserRoleName(role);
		userService.saveUser(user);
	}
	
	@GetMapping(value = "/admin/users/add")
	public String addNewUser(@RequestParam(name = "n") String username,
			@RequestParam(name = "e") String email, @RequestParam(name = "p")String passwords,
			@RequestParam(name = "r")String role) {
		
		UserDTO userDTO = new UserDTO(username, email, passwords);
		if(role.equals("ROLE_ADMIN")) {
			userService.saveAdmin(userDTO);
		}
		if(role.equals("ROLE_USER")) {
			userService.save(userDTO);
		}
		return "redirect:/admin/users";
	}
	
	@ResponseBody
	@GetMapping(value = "/admin/users/check-email")
	public boolean checkEmail(@RequestParam(name = "email")String email, @RequestParam(name = "id")Long id) {
		return userService.checkUserEmail(email, id);
	}
	
	
}
