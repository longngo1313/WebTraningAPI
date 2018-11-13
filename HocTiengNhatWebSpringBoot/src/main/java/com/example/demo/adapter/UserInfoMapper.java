package com.example.demo.adapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.UserInfo;


public class UserInfoMapper implements RowMapper<UserInfo>{

	public static final String BASE_SQL //
	= "Select u.User_Id, u.User_Name, u.Encryted_Password From App_User u ";

	@Override
	public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {

		Long userId = rs.getLong("User_Id");
		String userName = rs.getString("User_Name");
		String encrytedPassword = rs.getString("Encryted_Password");
		
		String[] roles= {"USER"};
		
		List<GrantedAuthority> grantList= new ArrayList<GrantedAuthority>();
		if(roles!= null)  {
			for(String role: roles)  {
				// ROLE_USER, ROLE_ADMIN,..
				GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);
				grantList.add(authority);
			}
		}       

		return new UserInfo(userName,encrytedPassword, grantList, userId);
	}

}
