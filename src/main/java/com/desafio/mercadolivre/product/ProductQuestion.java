package com.desafio.mercadolivre.product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.desafio.mercadolivre.user.User;

@Entity
public class ProductQuestion {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String title;
	@NotNull
	private LocalDateTime creationMoment = LocalDateTime.now();
	@Valid
	@NotNull
	@ManyToOne
	private User user;
	@Valid
	@NotNull
	@ManyToOne
	private Product product;
	
	public ProductQuestion() {};
	
	public ProductQuestion(@NotBlank String title, @Valid @NotNull User user,
			@Valid @NotNull Product product) {
		Assert.isTrue(StringUtils.hasLength(title), "Title must not be blank!");
		Assert.isTrue(user != null, "The User must not be null");
		Assert.isTrue(product != null, "The Product must not be null");
		Assert.isTrue(product.mayBeInterestOf(user), "A user should not be able to be interested in the product he owns!");
		this.title = title;
		this.user = user;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}

	public User getUser() {
		return user;
	}

	public Product getProduct() {
		return product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductQuestion other = (ProductQuestion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	public String formatCreationMoment(String pattern) {
		return this.creationMoment
				.format(DateTimeFormatter.ofPattern(pattern));
	}

	public User getProductOwner() {
		return this.product.getUser();
	}

}
