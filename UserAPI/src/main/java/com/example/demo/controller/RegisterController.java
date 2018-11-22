package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.UserDAO;
import com.example.demo.dao.UserRoleDAO;
import com.example.demo.model.UserInfo;
import com.example.demo.model.UserRegisterForm;
import com.example.demo.utils.AppUserValidator;

@Controller
public class RegisterController {

	@Autowired
	private AppUserValidator appUserValidator;

	@Autowired
	private UserDAO userInfoDAO;
	
	@Autowired
	private UserRoleDAO roleDAO;

	// Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == UserRegisterForm.class) {
			dataBinder.setValidator(appUserValidator);
		}
	}


	@RequestMapping("/registerSuccessful")
	public String viewRegisterSuccessful(Model model) {

		return "registerSuccessfulPage";
	}
	
	// Hiển thị trang đăng ký.
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String viewRegister(Model model) {

		UserRegisterForm form = new UserRegisterForm();

		model.addAttribute("appUserForm", form);

		return "registerPage";
	}

	// Phương thức này được gọi để lưu thông tin đăng ký.
	// @Validated: Để đảm bảo rằng Form này
	// đã được Validate trước khi phương thức này được gọi.
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveRegister(Model model, //
			@ModelAttribute("appUserForm") @Validated UserRegisterForm appUserForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
		// Validate result
		
		if (result.hasErrors()) {
			return "registerPage";
		}
		
		try {
			UserInfo newUser = userInfoDAO.registerUserByDirect(appUserForm);
			//TODO: Insert user role
			List<String> authorities = Arrays.asList("ROLE_USER");
			roleDAO.registerRoleForUser(newUser, authorities);
		}
		// Other error!!
		catch (Exception e) {
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
			return "registerPage";
		}

		redirectAttributes.addFlashAttribute("flashUser", appUserForm);

		return "redirect:/registerSuccessful";
	}


	@RequestMapping(value = { "/signup" }, method = RequestMethod.GET)
	public String signupPage(WebRequest request, Model model) {

		UserRegisterForm myForm = null;
		//
		myForm = new UserRegisterForm();
		model.addAttribute("appUserForm", myForm);
		return "registerPage";
	}

}
