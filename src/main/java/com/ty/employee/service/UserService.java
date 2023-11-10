package com.ty.employee.service;

 
import com.ty.employee.web.dto.UserRegisterationDto;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.ty.employee.model.User;

public interface UserService extends UserDetailsService{
	 User save(UserRegisterationDto registerationdto);
}
