package com.ecommerce.dto;

import java.util.UUID;


import com.ecommerce.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemDetail {
	 private UUID id;
	    private Product product;
	    private UUID productVariantId;
	    private Integer quantity;
	    private Double itemPrice;
}