package com.desafio.mercadolivre.user.response;

import com.desafio.mercadolivre.user.User;

public class UserResponseDTO {

	private String username;
	private String login;

	public UserResponseDTO(User user) {
		this.username = user.getUsername();
		this.login = user.getLogin();
	}

	public String getUsername() {
		return username;
	}

	public String getLogin() {
		return login;
	}
}
