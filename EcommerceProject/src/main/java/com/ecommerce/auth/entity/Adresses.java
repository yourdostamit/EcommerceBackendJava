package com.ecommerce.auth.entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Adresses {

	@Id
	@GeneratedValue
    private UUID id;

    private String name;
    
    @Column(nullable = false)
    private String street;
    
    @Column(nullable = false)
    private String city;
    
    @Column(nullable = false)
    private String state;
    
    @Column(nullable = false)
    private String ZipCode;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_Id" , nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private User user;
    
}
