package com.mmit.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mmit.model.entity.Users;
import com.mmit.model.service.UserService;

@Named
@RequestScoped
public class UserBean {

	private Users user;
	private List<Users> users;
	@Inject
	private UserService service;
	
	@PostConstruct
	private void init() {
		user=new Users();
		users=service.findAll();
	}
	
	public void checkEmail() {
		Users tmp_obj=service.findByEmail(user.getEmail());
		if(tmp_obj != null) {
			FacesContext cxt=FacesContext.getCurrentInstance();
			cxt.addMessage("create:email", new FacesMessage(tmp_obj.getEmail()+" already exist!"));
			cxt.validationFailed();
		}
	}
	
	public String createUser() {
		FacesContext cxt=FacesContext.getCurrentInstance();
		
		if(cxt.isValidationFailed())
			return null;
		service.createUser(user);
		return "/admin/admin-home.xhtml?faces-redirect=true";
	}
	
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Users> getUsers() {
		return users;
	}
	
	
}
