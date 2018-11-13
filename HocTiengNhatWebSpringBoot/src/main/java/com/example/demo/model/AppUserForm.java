package com.example.demo.model;

public class AppUserForm {
	 
    private String userName;
    private String password;
    private String confirmPassword;
    
    
    public AppUserForm() {
 
    }


	public AppUserForm(String userName, String password, String confirmPassword) {
		super();
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
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
    
    
 
   
}