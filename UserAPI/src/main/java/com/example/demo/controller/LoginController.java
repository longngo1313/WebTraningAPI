package com.example.demo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.model.UserInfo;




@Controller
public class LoginController {


	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		model.addAttribute("title", "Welcome");
		model.addAttribute("message", "This is welcome page!");
		return "welcomePage";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();

		model.addAttribute("userInfo", loginedUser.toString());

		return "adminPage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		return "loginPage";
	}

	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout");
		return "logoutSuccessfulPage";
	}

	@RequestMapping(value = "/userAccountInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {

		// (1) (en)
		// After user login successfully.
		// (vi)
		// Sau khi user login thanh cong se co principal
		String userName = principal.getName();

		System.out.println("User Name: " + userName);

		Object object = ((Authentication) principal).getPrincipal();
		if(object  instanceof User) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			String userInfo = loginedUser.toString();
			model.addAttribute("userInfo", userInfo);
		}

		return "userInfoPage";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			String userInfo = loginedUser.toString();

			model.addAttribute("userInfo", userInfo);

			String message = "Hi " + principal.getName() //
			+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

}
