package com.example.demo.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.UserInfo;
import com.example.demo.model.UserRegisterForm;
import com.example.demo.utils.EncrytedPasswordUtils;

@Repository
@Transactional
public class UserDAO {

	@Autowired
	private EntityManager entityManager;

	public UserInfo findUserByUserId(Long userId) {
		try {
			String sql = "SELECT e FROM "+  UserInfo.class.getName() +  " e WHERE e.userId = :userId ";

			Query query = entityManager.createQuery(sql, UserInfo.class);
			query.setParameter("userId", userId);

			return (UserInfo) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public UserInfo findUserByUserName(String userName) {
		try {
			String sql = "SELECT c FROM " +  UserInfo.class.getName() +   " c WHERE c.userName = :userName ";

			Query query = entityManager.createQuery(sql, UserInfo.class);
			query.setParameter("userName", userName);

			return (UserInfo) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	private String findAvailableUserName(String userName_prefix) {
		UserInfo account = this.findUserByUserName(userName_prefix);
		if (account == null) {
			return userName_prefix;
		}
		int i = 0;
		while (true) {
			String userName = userName_prefix + "_" + i++;
			account = this.findUserByUserName(userName);
			if (account == null) {
				return userName;
			}
		}
	}

	public UserInfo findUserByEmail(String email) {
		try {
			String sql = "SELECT e FROM " +  UserInfo.class.getName() + " e WHERE e.email = :email ";

			Query query = entityManager.createQuery(sql, UserInfo.class);
			query.setParameter("email", email);

			return (UserInfo) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

//
//	// Auto create App User Account via Facebook, Google account
//	public UserInfo registerUserBySocial(Connection<?> connection) {
//
//		ConnectionKey key = connection.getKey();
//		// (facebook,12345), (google,123) ...
//
//		System.out.println("key= (" + key.getProviderId() + "," + key.getProviderUserId() + ")");
//
//		UserProfile userProfile = connection.fetchUserProfile();
//		
//		System.out.println("User Profile is "+ userProfile.getUsername());
//		System.out.println("User Profile is "+ userProfile.getName());
//		String email = userProfile.getEmail();
//		UserInfo appUser = this.findUserByEmail(email);
//		if (appUser != null) {
//			return appUser;
//		}
//		String userName_prefix = userProfile.getFirstName().trim().toLowerCase()//
//				+ "_" + userProfile.getLastName().trim().toLowerCase();
//
//		String userName = this.findAvailableUserName(userName_prefix);
//		//
//		// Random Password! TODO: Need send email to User!
//		//
//		String randomPassword = UUID.randomUUID().toString().substring(0, 5);
//		String encrytedPassword = EncrytedPasswordUtils.encrytePassword(randomPassword);
//		
//		List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//		//
//		appUser = new UserInfo(userName, encrytedPassword, authorities);
//		appUser.setEncrytedPassword(encrytedPassword);
//		appUser.setUserName(userName);
//		appUser.setEmail(email);
//		appUser.setFirstName(userProfile.getFirstName());
//		appUser.setLastName(userProfile.getLastName());
//
//		this.entityManager.persist(appUser);
//
//		return appUser;
//	}
	
	public UserInfo registerUserByDirect(UserRegisterForm appUserForm) {
		
		String encrytedPassword = EncrytedPasswordUtils.encrytePassword(appUserForm.getPassword());
		
		UserInfo appUser = new UserInfo();
        appUser.setUserName(appUserForm.getUserName());
        appUser.setEncrytedPassword(encrytedPassword);
        appUser.setEmail(appUserForm.getUserName());
        
        this.entityManager.persist(appUser);
        
        return appUser;
    }
}
