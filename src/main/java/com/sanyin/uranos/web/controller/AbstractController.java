package com.sanyin.uranos.web.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class AbstractController {

	protected UserDetails getUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return (UserDetails) principal;
		}
		return null;
	}
}
