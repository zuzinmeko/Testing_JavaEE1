package com.mmit.security;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.mmit.model.entity.Users;
import com.mmit.model.entity.Users.Role;
import com.mmit.model.service.UserService;

@ApplicationScoped
@Singleton
@Startup
public class AdminUserCreation {

	@Inject
	private UserService service;
	
	@PostConstruct
	private void init() {
		
		long userCount=service.getCount();
		if(userCount == 0) {
			Users user=new Users();
			user.setEmail("ywk@gmail.com");
			user.setPassword("12345678");
			user.setRole(Role.Admin);
			user.setUserName("ywk");
			
			service.createUser(user);
		}
		
	}
}
