package com.desafio.mercadolivre.shared;


import java.util.Collection;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.desafio.mercadolivre.user.User;


public class LoggedInUser implements UserDetails {

	private User user;
	private org.springframework.security.core.userdetails.User springUserDetails;
	
	public LoggedInUser(@NotNull @Valid User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return springUserDetails.getAuthorities();
	}


	@Override
	public String getPassword() {
		 return user.getPassword();
	}

	@Override
	public String getUsername() {
		 return user.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return springUserDetails.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return springUserDetails.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return springUserDetails.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return springUserDetails.isEnabled();
	}
	
	public User get() {
		return user;
	}
	
}
