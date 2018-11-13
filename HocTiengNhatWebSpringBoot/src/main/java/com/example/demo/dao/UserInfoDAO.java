package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.adapter.UserInfoMapper;
import com.example.demo.model.AppUserForm;
import com.example.demo.model.UserInfo;
import com.example.demo.utils.EncrytedPasswordUtils;



@Service
@Transactional
public class UserInfoDAO extends JdbcDaoSupport {

	public static final String BASE_SQL //
	= "Select u.User_Id, u.User_Name, u.Encryted_Password From App_User u ";

	@Autowired
	public UserInfoDAO(DataSource dataSource) {
		this.setDataSource(dataSource);
	}


	public UserInfo findUserInfo(String userName) {
		String sql = BASE_SQL + " where u.User_Name = ? ";

		Object[] params = new Object[] { userName };
		UserInfoMapper mapper = new UserInfoMapper();
		try {
			UserInfo userInfo = this.getJdbcTemplate().queryForObject(sql, params, mapper);
			return userInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


	public List<String> getUserRoles(Long userId ) {
		//		String sql = "Select r.User_Role "//
		//				+ " from User_Role r where r.user_name = ? ";
		String sql = "Select r.Role_Name " //
				+ " from User_Role ur, App_Role r " //
				+ " where ur.Role_Id = r.Role_Id and ur.User_Id = ? ";
		Object[] params = new Object[] { userId  };

		List<String> roles = this.getJdbcTemplate().queryForList(sql,params, String.class);
		//		List<String> list = new ArrayList<>();
		//		list.add("ADMIN");
		return roles;
	}

	public List<UserInfo> getAllUsers() {
		String sql = BASE_SQL;
		UserInfoMapper mapper = new UserInfoMapper();
		try {
			List<UserInfo> listUserInfo = this.getJdbcTemplate().queryForList(sql, UserInfo.class, mapper);
			return listUserInfo;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public int createAppUser(AppUserForm form) {
		String encrytedPassword = EncrytedPasswordUtils.encrytePassword(form.getPassword());
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		UserInfo user = new UserInfo(form.getUserName(), encrytedPassword, authorities);
		System.out.println("Date insert is  " + user.getUsername() + " and " + encrytedPassword );
		String sql = "INSERT INTO App_User " //
				+ "( [User_Name], [Encryted_Password], [ENABLED] ) " //
				+ " VALUES ( ?, ? , ? )";
		try {
			int i = this.getJdbcTemplate().update(sql, user.getUsername(), encrytedPassword, 1);
			return i;
		} catch (Exception e) {
			return 0; //Error when insert User
		}
	}


	public int insertUserRole(List<String> listRoles, long userId) {
		System.out.println("ROLE USER ID " + userId);
		if (listRoles != null) {
			for (String role : listRoles) {
				String sql = "INSERT INTO User_Role " //
						+ "( [User_Id], [Role_Id]) " //
						+ " VALUES ( ?, ?)";
				try {
					int i = this.getJdbcTemplate().update(sql, userId, role);
				} catch (Exception e) {
					return 0;
				}
			}
		}
		return 1;
	}
}
