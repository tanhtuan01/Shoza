package com.tat.shoza.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tat.shoza.dto.UserDTO;
import com.tat.shoza.model.Role;
import com.tat.shoza.model.User;
import com.tat.shoza.repository.UserRepository;
import com.tat.shoza.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("=> Email not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswords(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
		// TODO Auto-generated method stub
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
	}

	@Override
	public User save(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = new User(
								userDTO.getUserName(), 
								userDTO.getEmail(), 
								passwordEncoder.encode(userDTO.getPasswords()), 
								Arrays.asList(new Role("ROLE_USER"))
							);
		return userRepository.save(user);
	}

	@Override
	public User saveAdmin(UserDTO userDTO) {
		// TODO Auto-generated method stub
		User user = new User(
				userDTO.getUserName(), 
				userDTO.getEmail(), 
				passwordEncoder.encode(userDTO.getPasswords()), 
				Arrays.asList(new Role("ROLE_ADMIN"))
			);
return userRepository.save(user);
	}
	
	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
	
	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public User getById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.getById(id);
	}
	
	@Override
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}
	
	@Override
	public boolean checkUserEmail(String email, Long id) {
		// TODO Auto-generated method stub
		if(id == 0) {
			return userRepository.existsByEmailEquals(email);
		}else {
			return userRepository.existsByEmailEqualsAndIdNot(email, id);
		}
	}
	
	@Override
	public Long countUser() {
		// TODO Auto-generated method stub
		return userRepository.count();
	}
}
