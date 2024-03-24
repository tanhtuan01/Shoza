package com.tat.shoza.dto;

public class UserRoleDTO {

	private Long id;
	
	private String userName;
	
	private String email;
	
	private String roleName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public UserRoleDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRoleDTO(Long id, String userName, String email, String roleName) {
		super();
		this.id = id;
		this.userName = userName;
		this.email = email;
		this.roleName = roleName;
	}
	
	
	
	
}
