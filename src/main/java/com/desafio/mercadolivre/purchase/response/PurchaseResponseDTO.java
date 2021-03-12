package com.desafio.mercadolivre.purchase.response;

import com.desafio.mercadolivre.product.response.ProductDetailResponseDTO;
import com.desafio.mercadolivre.purchase.PaymentMethod;
import com.desafio.mercadolivre.purchase.Purchase;
import com.desafio.mercadolivre.purchase.PurchaseStatus;
import com.desafio.mercadolivre.user.response.UserResponseDTO;

public class PurchaseResponseDTO {

	private PaymentMethod paymentMethod;
	private ProductDetailResponseDTO productDetailResponseDTO;
	private int quantity;
	private UserResponseDTO buyer;
	private PurchaseStatus purchaseStatus;
	

	public PurchaseResponseDTO(Purchase purchase) {
		this.paymentMethod = purchase.getPaymentMethod();
		this.productDetailResponseDTO = new ProductDetailResponseDTO(purchase.getProduct());
		this.quantity = purchase.getQuantity();
		this.buyer = new UserResponseDTO(purchase.getBuyer());
		this.purchaseStatus = purchase.getStatus();
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public ProductDetailResponseDTO getProductDetailResponseDTO() {
		return productDetailResponseDTO;
	}

	public int getQuantity() {
		return quantity;
	}

	public UserResponseDTO getBuyer() {
		return buyer;
	}

	public PurchaseStatus getPurchaseStatus() {
		return purchaseStatus;
	}
}
