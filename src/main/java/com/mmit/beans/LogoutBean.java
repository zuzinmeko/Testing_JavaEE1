package com.mmit.beans;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import jdk.jfr.Name;

@Named
@RequestScoped
public class LogoutBean {

	
	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		
		return "/index.xhtml?faces-redirect=true";
	}
}
