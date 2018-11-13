package com.example.demo.model;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserInfo extends User{
	
	private Long userId;
	
	
	public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}


	public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities,
			Long userId) {
		super(username, password, authorities);
		this.userId = userId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}
