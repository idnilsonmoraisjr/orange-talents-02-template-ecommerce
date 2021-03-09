package com.desafio.mercadolivre.product;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.desafio.mercadolivre.category.Category;
import com.desafio.mercadolivre.product.request.ProductAttributeRequest;
import com.desafio.mercadolivre.user.User;

class ProductTest {

	@DisplayName("A product must have a minimum of 3 distinct attribucts")
	@ParameterizedTest
	@MethodSource("generateTest1")
	void test1(Collection<ProductAttributeRequest> attributes) throws Exception{
		Category category = new Category("Category test");
		User user = new User("user@email.com", "123456");
		new Product("name", BigDecimal.TEN, 5, attributes,"Test description", category, user);
	}

	static Stream<Arguments> generateTest1() {
		return Stream.of(
				Arguments.of(
							List.of(new ProductAttributeRequest("key1", "value1"),
									new ProductAttributeRequest("key2", "value2"),
									new ProductAttributeRequest("key3", "value3"))),
				Arguments.of(
							List.of(new ProductAttributeRequest("key1", "value1"),
									new ProductAttributeRequest("key2", "value2"),
									new ProductAttributeRequest("key3", "value3"),
									new ProductAttributeRequest("key4", "value4"))));
	}
	
	@DisplayName("A product must have a minimum of 3 distinct attribucts")
	@ParameterizedTest
	@MethodSource("generateTest2")
	void test2(Collection<ProductAttributeRequest> attributes) throws Exception{
		Category category = new Category("Category test");
		User user = new User("user@email.com", "123456");
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Product("name", BigDecimal.TEN, 5, attributes,"Test description", category, user);
		});
	}

	static Stream<Arguments> generateTest2() {
		return Stream.of(
				Arguments.of(
							List.of(new ProductAttributeRequest("key1", "value1"),
									new ProductAttributeRequest("key2", "value2"))),
				Arguments.of(
							List.of(new ProductAttributeRequest("key3", "value3"))));
	}
}
