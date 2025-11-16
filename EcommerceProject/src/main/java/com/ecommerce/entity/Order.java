package com.ecommerce.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ecommerce.auth.entity.Adresses;
import com.ecommerce.auth.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name="orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
@Id
@GeneratedValue
private UUID id;


@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name= "user_id", nullable = false)
@JsonIgnore
private User user;

@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name= "address_id" , nullable = false)
@ToString.Exclude
@JsonIgnore
private Adresses address;

@Column(nullable = false)
private Double totalAmount;

@Column(nullable = true)
private String shipmentTrackingNumber;

@Enumerated(EnumType.STRING)
@Column(nullable = false)
private OrderStatus orderStatus;

@Column(nullable = false)
private String paymentMethod;

@Column(nullable = true)
@Temporal(TemporalType.TIMESTAMP)
private Date orderDate;

@Column(nullable = false)
private Date exceptedDeleveryDate;

@OneToMany(fetch =FetchType.LAZY,mappedBy = "order" , cascade = CascadeType.ALL)
@ToString.Exclude
private List<OrderItem> orderItemList;

private Double discount;

@OneToOne(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
 
private Payment payment;

}
