package com.ty.employee.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.hibernate.engine.spi.ExtendedSelfDirtinessTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ty.employee.model.Role;
import com.ty.employee.model.User;
import com.ty.employee.repository.UserRepository;
import com.ty.employee.web.dto.UserRegisterationDto;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegisterationDto registerationdto) {
		User user=new User(registerationdto.getFirstName(), registerationdto.getLastName(),
							registerationdto.getEmail(), registerationdto.getPassword(), Arrays.asList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		 User user=userRepository.findByEmail(username);
		 if(user==null) {
			 throw new UsernameNotFoundException("Invalid User name or Password");
		 }
		 return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),  mapRolesToAuthority(user.getRole()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthority(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		   
		
	}

}
