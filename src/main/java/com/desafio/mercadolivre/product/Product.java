package com.desafio.mercadolivre.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import com.desafio.mercadolivre.category.Category;
import com.desafio.mercadolivre.user.User;

@Entity
public class Product {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank
	private String name;
	@NotNull @Positive
	private BigDecimal price;
	@NotNull @Positive
	private int amount;
	@NotNull
	@OneToMany( mappedBy = "product", cascade = CascadeType.PERSIST)
	@Size(min = 3)
	private Set<ProductAttribute> productAttributes = new HashSet<>();
	@NotBlank 
	@Size(max = 1000)
	private  String description;
	@ManyToOne 
	@NotNull 
	private Category productCategory;
	@NotNull
	private LocalDateTime creationMoment = LocalDateTime.now();
	@NotNull
	@ManyToOne
	private User user;
	@OneToMany( mappedBy = "product", cascade = CascadeType.MERGE)
	Set<ProductImage> images = new HashSet<>();
	
	@Deprecated
	public Product() {
		
	}
	
	public Product(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @Positive int amount,
			@NotNull @Size(min = 3) Collection<ProductAttributeRequest> productAttributes, @NotBlank @Size(max = 1000) String description,
			@NotNull Category productCategory, @NotNull User user) {
			Assert.isTrue(StringUtils.hasLength(name), "Name must not be blank!");
			Assert.isTrue(amount > 0, "The product must have a amount higher than zero!");
			Assert.isTrue(StringUtils.hasLength(description), "Description must not be blank!");
				this.name = name;
				this.price = price;
				this.amount = amount;
				this.productAttributes.addAll(productAttributes.stream()
						.map(attribute -> attribute.toModel(this))
						.collect(Collectors.toSet()));
				this.description = description;
				this.productCategory = productCategory;
				this.user = user;
				
				Assert.isTrue(productAttributes.size() >= 3, "The attibute must have a minimum of 3 distinct attributes");
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public int getAmount() {
		return amount;
	}

	public Set<ProductAttribute> getProductAttributes() {
		return productAttributes;
	}

	public String getDescription() {
		return description;
	}

	public Category getProductCategory() {
		return productCategory;
	}
	
	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}

	public User getUser() {
		return user;
	}
	
	public Set<ProductImage> getImage() {
		return images;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", amount=" + amount
				+ ", productAttributes=" + productAttributes + ", description=" + description + ", productCategory="
				+ productCategory + ", creationMoment=" + creationMoment + ", user=" + user + ", images=" + images
				+ "]";
	}

	public String formatCreationMoment(String pattern) {
		return this.creationMoment
				.format(DateTimeFormatter.ofPattern(pattern));
	}

	public boolean checkProductOwner(User authenticatedUser) {
		if(!this.user.equals(authenticatedUser)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN,
					"This product not belong this user!");
		}
		 return true;
	}

	public void checkImages(Set<String> links) {
		Set<ProductImage> images = links.stream().map(link -> new ProductImage(link, this))
				.collect(Collectors.toSet());
		
		this.images.addAll(images);
	}
}
