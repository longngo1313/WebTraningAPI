package com.example.demo.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.dao.UserDAO;
import com.example.demo.model.UserInfo;
import com.example.demo.model.UserRegisterForm;
 
@Component
public class AppUserValidator implements Validator {
 
    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();
 
    @Autowired
    private UserDAO appUserDAO;
 
    // Các lớp được hỗ trợ bởi Validator này.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserRegisterForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
    	UserRegisterForm appUserForm = (UserRegisterForm) target;
 
        // Kiểm tra các field của AppUserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty.appUserForm.email");

        if (!this.emailValidator.isValid(appUserForm.getEmail())) {
            // Email không hợp lệ.
            errors.rejectValue("email", "Pattern.appUserForm.email");
        } 
        
        if (!errors.hasFieldErrors("userName")) {
        	UserInfo dbUser = appUserDAO.findUserByUserName(appUserForm.getUserName());
            if (dbUser != null) {
                // Tên tài khoản đã bị sử dụng bởi người khác.
                errors.rejectValue("userName", "Duplicate.appUserForm.userName");
            }
        }
 
        if (!errors.hasErrors()) {
            if (!appUserForm.getConfirmPassword().equals(appUserForm.getPassword())) {
                errors.rejectValue("confirmPassword", "Match.appUserForm.confirmPassword");
            }
        }
    }
 
}