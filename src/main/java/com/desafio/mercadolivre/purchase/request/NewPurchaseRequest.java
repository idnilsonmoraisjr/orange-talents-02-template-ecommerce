package com.desafio.mercadolivre.purchase.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.purchase.PaymentMethod;
import com.desafio.mercadolivre.purchase.Purchase;
import com.desafio.mercadolivre.user.User;

public class NewPurchaseRequest {

	@NotNull
	@Positive
	private int quantity;
	
	@NotNull
	private PaymentMethod paymentMethod;

	public NewPurchaseRequest(@NotNull @Positive int quantity, @NotNull PaymentMethod paymentMethod) {
		this.quantity = quantity;
		this.paymentMethod = paymentMethod;
	}

	public Purchase toModel(User user, Product product) {
		return new Purchase(user, product, this.quantity, this.paymentMethod);
	}
	
	
}
