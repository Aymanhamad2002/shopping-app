package com.backendProject.shoppingApp.model;

import java.math.BigDecimal;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product ;
	private int quantity;
	private BigDecimal unitPrice;
	private BigDecimal totalPrice;
	@ManyToOne()
	@JoinColumn(name = "cart_id")
	@JsonIgnore
	private Cart cart;
	public void setTotalPrice() {
		this.totalPrice =this.unitPrice.multiply(new BigDecimal(quantity));
	}

}
