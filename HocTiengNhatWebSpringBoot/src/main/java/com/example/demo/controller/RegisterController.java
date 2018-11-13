package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.UserInfoDAO;
import com.example.demo.model.AppUserForm;
import com.example.demo.model.UserInfo;
import com.example.demo.utils.AppUserValidator;

@Controller
public class RegisterController {

	@Autowired
	private AppUserValidator appUserValidator;

	@Autowired
	private UserInfoDAO userInfoDAO;

	// Set a form validator
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		// Form mục tiêu
		Object target = dataBinder.getTarget();
		if (target == null) {
			return;
		}
		System.out.println("Target=" + target);

		if (target.getClass() == AppUserForm.class) {
			dataBinder.setValidator(appUserValidator);
		}
	}


	@RequestMapping("/registerSuccessful")
	public String viewRegisterSuccessful(Model model) {

		return "registerSuccessfulPage";
	}

	@RequestMapping("/members")
	public String viewMembers(Model model) {

		List<UserInfo> list = userInfoDAO.getAllUsers();

		model.addAttribute("members", list);

		return "membersPage";
	}

	// Hiển thị trang đăng ký.
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String viewRegister(Model model) {

		AppUserForm form = new AppUserForm();

		model.addAttribute("appUserForm", form);

		return "registerPage";
	}

	// Phương thức này được gọi để lưu thông tin đăng ký.
	// @Validated: Để đảm bảo rằng Form này
	// đã được Validate trước khi phương thức này được gọi.
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveRegister(Model model, //
			@ModelAttribute("appUserForm") @Validated AppUserForm appUserForm, //
			BindingResult result, //
			final RedirectAttributes redirectAttributes) {
		// Validate result
		if (result.hasErrors()) {
			return "registerPage";
		}
		int i= 0;
		try {
			i = userInfoDAO.createAppUser(appUserForm);
			System.out.println("Number of row affected " + i);
			UserInfo appUser = this.userInfoDAO.findUserInfo(appUserForm.getUserName());	
			List<String> roleNames = new ArrayList<>();
			roleNames.add("1");
			userInfoDAO.insertUserRole(roleNames, appUser.getUserId());
		}
		// Other error!!
		catch (Exception e) {
			model.addAttribute("errorMessage", "Error: " + e.getMessage());
			return "registerPage";
		}

		redirectAttributes.addFlashAttribute("flashUser", appUserForm);

		return "redirect:/registerSuccessful";
	}

}
