package com.desafio.mercadolivre.product;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class NewProductPostRequestTest {

	@ParameterizedTest
	@DisplayName("A product must have distinct name attribucts")
	@MethodSource("generateTest")
	void test1(int expected, List<ProductAttributeRequest> attributes) throws Exception{
		NewProductPostRequest request = new NewProductPostRequest("name", BigDecimal.TEN, 5,
				attributes, "Description Test", 1L);
		
		Assertions.assertEquals(expected, request.findEqualAttributes().size());
	}

	private static Stream<Arguments> generateTest() {
		return Stream.of(
				Arguments.of(0, List.of()),
				Arguments.of(0, List.of(new ProductAttributeRequest("key", "value"))),
				Arguments.of(0, List.of(
						new ProductAttributeRequest("key1", "value1"),
						new ProductAttributeRequest("key2", "value2"))),
				Arguments.of(1, List.of(
						new ProductAttributeRequest("key", "value"),
						new ProductAttributeRequest("key", "value")))
				);
	}
}
