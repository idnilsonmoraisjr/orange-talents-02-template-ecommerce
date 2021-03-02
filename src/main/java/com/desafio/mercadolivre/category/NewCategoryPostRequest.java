package com.desafio.mercadolivre.category;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

import org.springframework.util.Assert;

import com.desafio.mercadolivre.shared.ExistsId;
import com.desafio.mercadolivre.shared.UniqueValue;

public class NewCategoryPostRequest {

	@NotBlank
	@UniqueValue(domainClass=Category.class, fieldName= "name")
	String name;
	@ExistsId(domainClass = Category.class, fieldName="id")
	Long idParentCategory; 

	public void setName(String name) {
		this.name = name;
	}

	public void setIdParentCategory(Long idParentCategory) {
		this.idParentCategory = idParentCategory;
	}

	public String getName() {
		return name;
	}
	
	public Long getCategoryParent() {
		return idParentCategory;
	}

	public Category toModel(EntityManager entityManager) {
		Category category = new Category(this.name);
		
		if(idParentCategory != null) {
			Optional<Category> optionalCategory = Optional.ofNullable(entityManager
					.find(Category.class, idParentCategory));
			Assert.state(optionalCategory.isPresent(), "This category does not exists!");
			category.setParentCategory(optionalCategory.get());
		}
		return category;
	}
	
}
