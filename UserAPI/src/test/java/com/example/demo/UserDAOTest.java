package com.example.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.UserInfo;
import com.example.demo.model.UserRegisterForm;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDAOTest {

	@Autowired 
	private UserDAO userDao;
	
	private static final String userName = "user14";
	
	private static final String email = "user14";
	
	private static ConnectionData connectionData = new ConnectionData("providerId",
            "providerUserId",
            "displayName",
            "profileUrl",
            "imageUrl",
            "accessToken",
            "secret",
            "refreshToken",
            1000L);

	private static UserProfile userProfile = new UserProfileBuilder()
           .setEmail("email")
           .setFirstName("firstName")
           .setLastName("lastName")
           .build();
	
	private static Random random = new Random();
	
	private static UserRegisterForm userForm = new UserRegisterForm(("user" + random.nextInt(100)), "1","1", "USER_ROLE");
	
	private static List<String> roles = Arrays.asList("ROLE_USER");
	
	@Test
	public void findAppUserByUserNameTest() {
		UserInfo info = userDao.findUserByUserName(userName);
		assertNotNull(info);
	}
	
	@Test
	public void findAppUserByUserIdTest() {
		UserInfo shouldNotNullUser = userDao.findUserByUserId(2l);
		assertNotNull(shouldNotNullUser);
		UserInfo shouldNullUser = userDao.findUserByUserId(1l);
		assertNull(shouldNullUser);
	}
	
	@Test
	public void findUserByEmailTest() {
		UserInfo info = userDao.findUserByEmail(email);
		assertNotNull(info);
	}
	
//	@Test
//	public void registerUserBySocialTest() {
//		TestConnection connection = new TestConnection(connectionData, userProfile);
//		UserInfo info = userDao.registerUserBySocial(connection);
//		
//		UserInfo checkUser = userDao.findUserByUserName(info.getUserName());
//		assertNotNull(info);
//		assertNotNull(checkUser);
//		assertEquals(info.getUserName(), checkUser.getUserName());
//	}
	
	
	@Test
	public void registerUserByDirectTest() {
		UserInfo info = userDao.registerUserByDirect(userForm);
		UserInfo checkUser = userDao.findUserByUserName(info.getUserName());
		assertNotNull(info);
		assertNotNull(checkUser);
		assertEquals(info.getUserName(), checkUser.getUserName());
	}
	
	
}
