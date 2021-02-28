package com.desafio.mercadolivre.user;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Entity
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Email
	@NotBlank
	private String login;
	@NotBlank
	@Length(min = 6)
	private String password;
	@NotNull
	@Column(name = "creation_moment")
	private LocalDateTime creationMoment = LocalDateTime.now();
	
	@Deprecated
	public User() {}
	
	public User(@Email @NotBlank String login, @NotBlank @Length(min = 6) String password) {
		Assert.isTrue(StringUtils.hasLength(login), "Login must not be blank!");
		Assert.isTrue(password.length() >= 6, "Password must be at least 6 characters");
		this.login = login;
		this.password = new BCryptPasswordEncoder().encode(password);
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}
		
	public String formatCreationMoment(String pattern) {
		return this.creationMoment
				.format(DateTimeFormatter.ofPattern(pattern));
	}
}
