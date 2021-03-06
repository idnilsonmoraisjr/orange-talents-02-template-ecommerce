package com.desafio.mercadolivre.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import com.desafio.mercadolivre.product.request.ProductAttributeRequest;
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
	@NotNull 
	private int quantity;
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
	@Column(name = "creation_moment", updatable = false)
	private LocalDateTime creationMoment = LocalDateTime.now();
	@NotNull
	@ManyToOne
	private User user;
	@OneToMany( mappedBy = "product", cascade = CascadeType.MERGE)
	Set<ProductImage> images = new HashSet<>();
	@OneToMany(mappedBy = "product")
	List<ProductQuestion> questions = new ArrayList<>();
	@OneToMany(mappedBy = "product", cascade = CascadeType.MERGE)
	List<ProductImpression> impressions = new ArrayList<>();
	
	@Deprecated
	public Product() {}
	
	public Product(@NotBlank String name, @NotNull @Positive BigDecimal price, @NotNull @Positive int quantity,
			@NotNull @Size(min = 3) Collection<ProductAttributeRequest> productAttributes, @NotBlank @Size(max = 1000) String description,
			@NotNull Category productCategory, @NotNull User user) {
			Assert.isTrue(StringUtils.hasLength(name), "Name must not be blank!");
			Assert.isTrue(quantity > 0, "The product must have a quantity higher than zero!");
			Assert.isTrue(StringUtils.hasLength(description), "Description must not be blank!");
				this.name = name;
				this.price = price;
				this.quantity = quantity;
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

	public int getQuantity() {
		return quantity;
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
	
	public List<ProductQuestion> getQuestions() {
		return questions;
	}
	
	public List<ProductImpression> getImpressions() {
		return impressions;
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
	
	public String formatCreationMoment(String pattern) {
		return this.creationMoment
				.format(DateTimeFormatter.ofPattern(pattern));
	}

	public boolean belongsToTheUser(User authenticatedUser) {
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
	
	public boolean mayBeInterestOf(User authenticatedUser) {
		 if(this.user.getId().equals(authenticatedUser.getId())) {
			 throw new ResponseStatusException(HttpStatus.FORBIDDEN,
						"A user should not be able to be interested in the product he owns!!");
		 }
		return true;
	}

	public <T> Set<T> imagesOf(Function<ProductImage, T> function) {
		return this.images.stream()
				.map(function)
				.collect(Collectors.toSet());
	}
	
	public <T> Set<T> attributesOf(Function<ProductAttribute, T> function) {
		return this.productAttributes.stream()
				.map(function)
				.collect(Collectors.toSet());
	}
	
	public <T> List<T> impressionsOf(Function<ProductImpression, T> function) {
		return this.impressions.stream()
				.map(function)
				.collect(Collectors.toList());
	}
	
	public <T> List<T> questionsOf(Function<ProductQuestion, T> function) {
		return this.questions.stream()
				.map(function)
				.collect(Collectors.toList());
	}
	
	public double gradeAverageImpressions() {
		List<Double> grades =  impressionsOf(impression -> impression.getGrade());
		OptionalDouble gradeAverage = grades.stream()
				.mapToDouble(grade -> grade)
				.average();
				
		return gradeAverage.orElse(0.0);
	}

	public boolean descreasesStock(@Positive int quantity) {
		Assert.isTrue(quantity > 0, "The product must have a quantity higher than zero!");
		if(this.quantity>=quantity) {
			this.quantity-=quantity;
			return true;
		}
		return false;
	}
}
