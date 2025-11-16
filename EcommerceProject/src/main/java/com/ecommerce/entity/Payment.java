package com.ecommerce.entity;

import java.sql.Date;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

	 @Id
	 @GeneratedValue
	 private UUID id;
	 
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name= "order_id" , nullable = false)
	 @JsonIgnore
	 @ToString.Exclude
	 private Order order;
	 
	 
	 @Column(nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    private java.util.Date paymentDate;
	 
	 @Column(nullable = false)
	    private Double amount;

	    @Column(nullable = false)
	    private String paymentMethod;
	    
	    
	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private PaymentStatus paymentStatus;
	    
	 
	 
	 
	
}
