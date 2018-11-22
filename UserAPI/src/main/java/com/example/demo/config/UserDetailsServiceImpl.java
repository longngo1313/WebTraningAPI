package com.example.demo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDAO;
import com.example.demo.dao.UserRoleDAO;
import com.example.demo.model.UserInfo;



@Service
public class UserDetailsServiceImpl  implements UserDetailsService {
	
	@Autowired
	private UserDAO userInfoDAO;
	
	@Autowired
	private UserRoleDAO roleDAO;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserInfo appUser = this.userInfoDAO.findUserByUserName(userName);
		 
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
 
        System.out.println("Found User: " + appUser.getUserName());
 
        
        
        List<String> listRoles = roleDAO.findListRoleByUserId(appUser.getUserId());
        List<GrantedAuthority> grantList = new ArrayList<>();
        
        if(listRoles != null && !listRoles.isEmpty()) {
        	for(String role: listRoles) {
        		grantList.add(new SimpleGrantedAuthority(role));
        	}
        }
 
        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncrytedPassword(), grantList);
 
        return userDetails;
    }

}
