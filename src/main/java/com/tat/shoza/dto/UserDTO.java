package com.tat.shoza.dto;


public class UserDTO {

	private Long id;

	private String userName;

	private String email;

	private String passwords;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passWords) {
		this.passwords = passWords;
	}

	public UserDTO(String userName, String email, String passwords) {
		super();
		this.userName = userName;
		this.email = email;
		this.passwords = passwords;
	}

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "UserDTO [userName=" + userName + ", email=" + email + ", passwords=" + passwords + "]";
	}
	
	
}
