package com.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

	private UUID id;
	private String name;
	private String description;
	private BigDecimal price;
	private String brand;
	private String slug;
	private boolean isNewArrival;
	private float rating;
	private String thumbnail;
	@JsonProperty("categoryId")
	private UUID categoryId;
	@JsonProperty("categoryTypeId")
	private UUID categoryTypeId;
	private String categoryName;
	private String categoryTypeName;
	private List<ProductVarientDto> varients;
	private List<ProductResourceDto> productResource;
	
}
