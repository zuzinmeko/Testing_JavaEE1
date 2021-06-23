package com.mmit.model.service;

import java.security.Principal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import com.mmit.model.entity.Users;
import com.mmit.security.CommonException;

@Stateless
public class UserService {

	@Inject
	private Pbkdf2PasswordHash encoder;
	
	@PersistenceContext
	private EntityManager em;

	@Inject
	Principal principal;
	
	public void createUser(Users user) {
		
		user.setPassword(encoder.generate(user.getPassword().toCharArray()));
		em.persist(user);
	}

	public List<Users> findAll() {
		TypedQuery<Users> query=em.createNamedQuery("Users.findAll", Users.class);
		query.setParameter("email", principal.getName());// return loginId (email)
		return query.getResultList();
	}

	public Users findByEmail(String email) {
		TypedQuery<Users> query=em.createNamedQuery("Users.findByEmail", Users.class);
		query.setParameter("email", email);
		Users u=null;
		try {
			u= query.getSingleResult();
		} catch (NoResultException e) {
			u=null;
		}
		return u;
	}

	public void changePassword(String oldPassword, String newPassword, String confirmPassword) {
		Users loginUser=findByEmail(principal.getName());
		
		if(!encoder.verify(oldPassword.toCharArray(), loginUser.getPassword())) {
			// not match old password
			throw new CommonException("Incorrect Old Password!");
		}
		if(!newPassword.equals(confirmPassword)) {
			throw new CommonException("confirmPassword do not match!");
		}
		
		loginUser.setPassword(encoder.generate(newPassword.toCharArray()));
		
	}

	public long getCount() {
		TypedQuery<Long> query=em.createNamedQuery("Users.getCount", Long.class);
		return query.getSingleResult();
	}
}
