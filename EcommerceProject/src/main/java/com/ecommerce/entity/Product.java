package com.ecommerce.entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

	
	@Id
	@GeneratedValue
	private UUID id;
	
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	 @Column(nullable = false)
	    private BigDecimal price;

	    @Column(nullable = false)
	    private String brand;

	    @Column
	    private Float rating;

	    @Column(nullable = false)
	    private boolean isNewArrival;

	    @Column(nullable = false,unique = true)
	    private String slug;
	
	 
	    @Column(nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    private java.util.Date createdAt;
	    
	    
	    @Column(nullable = false)
	    @Temporal(TemporalType.TIMESTAMP)
	    private java.util.Date updatedAt;
	    
	   
	    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
        private List<ProductVarient> productVarients;	    
	    
	    
	    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
	    private List<Resources> resources;
	    
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name= "category_id", nullable = false)
	    @JsonIgnore
	    private Category category;
	    
	    
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "categoryType_id",nullable = false)
	    @JsonIgnore
	    private CategoryType categoryType;
	    
	    @PrePersist
	    protected void onCreate() {
	    	createdAt = new java.util.Date();
	    	updatedAt= createdAt;
	    }
	    
	    @PreUpdate
	    protected void onUpdate() {
	    	updatedAt = new java.util.Date();
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
