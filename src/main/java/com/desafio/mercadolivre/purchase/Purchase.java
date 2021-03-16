package com.desafio.mercadolivre.purchase;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import com.desafio.mercadolivre.product.Product;
import com.desafio.mercadolivre.purchase.enums.PaymentMethod;
import com.desafio.mercadolivre.purchase.enums.PaymentStatus;
import com.desafio.mercadolivre.purchase.enums.PurchaseStatus;
import com.desafio.mercadolivre.purchase.response.PaymentMethodResponse;
import com.desafio.mercadolivre.user.User;

@Entity
public class Purchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Valid
	@ManyToOne
	private User buyer;
	@NotNull
	@Positive
	private int quantity;
	@NotNull
	@Column(name = "payment_method")
	private PaymentMethod paymentMethod;
	@ManyToOne
	@NotNull
	@Valid
	private Product product;
	@NotNull
	private PurchaseStatus status = PurchaseStatus.STARTED;
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
	private Set<Payment> payments = new HashSet<>();

	@Deprecated
	public Purchase() {
	}

	public Purchase(@NotNull @Valid User user, @NotNull @Valid Product product, @NotNull @Positive int quantity,
			@NotNull PaymentMethod paymentMethod) {

		Assert.notNull(user, "The User must not be null");
		Assert.isTrue(product.mayBeInterestOf(user),
				"A user should not be able to be interested in the product he owns!");
		Assert.notNull(product, "The Product must not be null");
		Assert.notNull(quantity, "The quantity must not be null");
		Assert.isTrue(quantity >= 0, "The purchase must have a amount higher than zero!");
		Assert.notNull(paymentMethod, "The Payment Method must not be null");
		this.buyer = user;
		this.quantity = quantity;
		this.paymentMethod = paymentMethod;
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public User getBuyer() {
		return buyer;
	}

	public int getQuantity() {
		return quantity;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public Product getProduct() {
		return product;
	}

	public PurchaseStatus getStatus() {
		return status;
	}

	public User getProductOwner() {
		return product.getUser();
	}

	public Set<PaymentStatus> getPaymentStatus() {
		return payments.stream()
				.map(Payment::getStatus)
				.collect(Collectors.toSet());
	}

	public String generatedRedirectUrl(UriComponentsBuilder uriComponentsBuilder) {
		return this.paymentMethod.createReturnUrl(this, uriComponentsBuilder);
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", buyer=" + buyer + ", quantity=" + quantity + ", paymentMethod=" + paymentMethod
				+ ", product=" + product + ", status=" + status + ", payments=" + payments + "]";
	}

	public void addPayment(@Valid PaymentMethodResponse request) {
		Payment payment = request.toPayment(this);
		Assert.isTrue(!this.payments.contains(payment), "There is already a transaction equal to this payment!");

		Assert.isTrue(successfullyPayments().isEmpty(), "There is already a successful payment on this transaction");

		this.payments.add(request.toPayment(this));
	}

	private Set<Payment> successfullyPayments() {
		Set<Payment> successfullyPayments = this.payments.stream().filter(Payment::successfullyPayment)
				.collect(Collectors.toSet());
		return successfullyPayments;
	}

	public boolean successfullyProcessed() {
		return !successfullyPayments().isEmpty();
	}

}
