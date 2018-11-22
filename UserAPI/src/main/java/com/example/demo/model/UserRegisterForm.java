package com.example.demo.model;


public class UserRegisterForm {

	private String userName;
	private String password;
	private String confirmPassword;
	private String email;
	


	public UserRegisterForm() {

	}

	public UserRegisterForm(String userName, String password, String confirmPassword, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getConfirmPassword() {
		return confirmPassword;
	}


	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
