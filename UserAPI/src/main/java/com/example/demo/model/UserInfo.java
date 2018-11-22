package com.example.demo.model;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Entity
@Table(name = "app_user", //
uniqueConstraints = { //
		@UniqueConstraint(name = "APP_USER_UK", columnNames = "User_Name")})
public class UserInfo{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserInfo() {
	}


	@Id
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@Column(name = "User_Id", nullable = false)
	private Long userId;

	@Column(name = "User_Name", length = 36, nullable = false)
	private String userName;

	@Column(name = "Email", length = 128, nullable = false)
	private String email;

	@Column(name = "First_Name", length = 36, nullable = true)
	private String firstName;

	@Column(name = "Last_Name", length = 36, nullable = true)
	private String lastName;

	@Column(name = "Encryted_Password", length = 128, nullable = false)
	private String encrytedPassword;

	@Column(name = "Enabled", length = 1, nullable = false)
	private int enabled;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEncrytedPassword() {
		return encrytedPassword;
	}

	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

}
