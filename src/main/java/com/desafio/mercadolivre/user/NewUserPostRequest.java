package com.desafio.mercadolivre.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

public class NewUserPostRequest {
	
	@Email
	@NotBlank
	private String login;
	@NotBlank
	@Length(min = 6)
	private String password;
	
	public NewUserPostRequest(
			@Email @NotBlank String login,
			@NotBlank @Length(min = 6) String password) {
		this.login = login;
		this.password = password;
	}
	
	public String getLogin() {
		return login;
	}
	public String getPassword() {
		return password;
	}

	public User toModel() {
		return new User(this.login, this.password);
	}
}
