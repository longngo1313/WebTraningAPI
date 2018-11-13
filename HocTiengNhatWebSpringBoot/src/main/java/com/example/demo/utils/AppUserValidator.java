package com.example.demo.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.example.demo.dao.UserInfoDAO;
import com.example.demo.model.AppUserForm;
import com.example.demo.model.UserInfo;
 
@Component
public class AppUserValidator implements Validator {
 
    // common-validator library.
    private EmailValidator emailValidator = EmailValidator.getInstance();
 
    @Autowired
    private UserInfoDAO appUserDAO;
 
    // Các lớp được hỗ trợ bởi Validator này.
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == AppUserForm.class;
    }
 
    @Override
    public void validate(Object target, Errors errors) {
        AppUserForm appUserForm = (AppUserForm) target;
 
        // Kiểm tra các field của AppUserForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "NotEmpty.appUserForm.userName");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.appUserForm.password");
 
        if (!errors.hasFieldErrors("userName")) {
        	UserInfo dbUser = appUserDAO.findUserInfo(appUserForm.getUserName());
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