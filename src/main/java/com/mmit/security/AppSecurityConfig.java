package com.mmit.security;

import javax.annotation.security.DeclareRoles;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.FormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;

@ApplicationScoped
@DeclareRoles({"Admin","Member"})
@DatabaseIdentityStoreDefinition(
		dataSourceLookup = "java:/users_ds",
		groupsQuery = "SELECT role FROM Users WHERE email=?",
		callerQuery = "SELECT password FROM Users WHERE email=?"
		)
@FormAuthenticationMechanismDefinition
(
loginToContinue = @LoginToContinue
(
		loginPage = "/login.xhtml",
		errorPage = "/403.xhtml"
)
)
public class AppSecurityConfig {

}
