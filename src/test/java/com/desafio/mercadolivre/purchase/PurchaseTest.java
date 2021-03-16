package com.desafio.mercadolivre.purchase;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.desafio.mercadolivre.category.Category;
import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.product.request.ProductAttributeRequest;
import com.desafio.mercadolivre.purchase.enums.PaymentMethod;
import com.desafio.mercadolivre.purchase.enums.PaymentStatus;
import com.desafio.mercadolivre.purchase.response.PaymentMethodResponse;
import com.desafio.mercadolivre.user.User;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@Transactional
@ActiveProfiles("test")
class PurchaseTest {

	@Autowired
	EntityManager manager;
	
	
	@Test
	@DisplayName("should add a payment")
	void test1() {
		Purchase newPurchase  = newPurchase();
		PaymentMethodResponse paymentMethodResponse = (purchase) -> {
			return new Payment("1",PaymentStatus.success, purchase);
		};
		
		newPurchase.addPayment(paymentMethodResponse);
	}
	
	@Test
	@DisplayName("shouldn't add more than one successfully payment")
	void test2() {
		Purchase newPurchase  = newPurchase();
		PaymentMethodResponse paymentMethodResponse = (purchase) -> {
			return new Payment("1",PaymentStatus.success, purchase);
		};
		
		newPurchase.addPayment(paymentMethodResponse);
		
		PaymentMethodResponse paymentMethodResponse2 = (purchase) -> {
			return new Payment("2",PaymentStatus.success, purchase);
		};
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			newPurchase.addPayment(paymentMethodResponse2);
		});
	}
	
	@Test
	@DisplayName("shouldn't accept a payment failure when it has already been successful")
	void test3() {
		Purchase newPurchase  = newPurchase();
		PaymentMethodResponse paymentMethodResponse = (purchase) -> {
			return new Payment("1",PaymentStatus.success, purchase);
		};
		
		newPurchase.addPayment(paymentMethodResponse);
		
		PaymentMethodResponse paymentMethodResponse2 = (purchase) -> {
			return new Payment("2",PaymentStatus.fail, purchase);
		};
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			newPurchase.addPayment(paymentMethodResponse2);
		});
	}
	
	private Purchase newPurchase() {
		Category category = new Category("teste");
		User owner = new User("email@email.com", "123456");
		manager.persist(owner);
		Collection<ProductAttributeRequest> attributes = new HashSet<>();
		attributes.add(new ProductAttributeRequest("name", "description"));
		attributes.add(new ProductAttributeRequest("name1", "description1"));
		attributes.add(new ProductAttributeRequest("name2", "description2"));

		Product product = new Product("test", BigDecimal.TEN, 10, attributes, "description",
				category, owner);
		User buyer = new User("buyer@email.com", "123456");

		PaymentMethod paymentMethod = PaymentMethod.PAGSEGURO;

		return new Purchase(buyer, product, 50, paymentMethod);
	}
}
