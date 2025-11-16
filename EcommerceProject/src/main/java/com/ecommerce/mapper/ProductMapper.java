package com.ecommerce.mapper;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.dto.ProductResourceDto;
import com.ecommerce.dto.ProductVarientDto;
import com.ecommerce.entity.Category;
import com.ecommerce.entity.CategoryType;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductVarient;
import com.ecommerce.entity.Resources;
import com.ecommerce.service.CategoryService;

@Component
public class ProductMapper {

	@Autowired
	private CategoryService categoryService;
	
	public  Product mapToProductEntity(ProductDto productDto) {
		
		Product product = new Product();
		
		if(null != productDto.getId()) {
			product.setId(productDto.getId());
			
		}
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setBrand(productDto.getBrand());
		product.setPrice(productDto.getPrice());
		product.setSlug(productDto.getSlug());
		product.setRating(productDto.getRating());
		product.setNewArrival(productDto.isNewArrival());
		
		 if (productDto.getCategoryId() == null) {
		        throw new IllegalArgumentException("categoryId must not be null in ProductDto");
		    }
		
		Category category= categoryService.getCategory(productDto.getCategoryId());
		
		if(null != category) {
			product.setCategory(category);
			
			UUID categoryTypeId = productDto.getCategoryTypeId();
				CategoryType categoryType = category.getCategoryType().stream().filter(
						categoryType1 ->categoryType1.getId().equals(categoryTypeId)
						).findFirst().orElse(null);
				product.setCategoryType(categoryType);;
						
		}
		
		
		if(null !=productDto.getVarients()) {
			product.setProductVarients(mapToProductVarients(productDto.getVarients(),product));
		}
		
		if(null != productDto.getProductResource()) {
			product.setResources(mapToProductResources(productDto.getProductResource(),product));
		}
		
			return product;
	}
	
	
	
	private List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product){
		return productResources.stream().map(productResourcesDto->{
			Resources resources= new Resources();
			if(null !=  productResourcesDto.getId()) {
				resources.setId(productResourcesDto.getId());			
				}
		
		resources.setName(productResourcesDto.getName());
		resources.setType(productResourcesDto.getType());
		resources.setUrl(productResourcesDto.getUrl());
		resources.setIsPrimary(productResourcesDto.getIsPrimary());
		resources.setProduct(product);
		return resources;
		}).collect(Collectors.toList());
				
	}
	

	
	
	
	
	private List<ProductVarient> mapToProductVarients(List<ProductVarientDto> productVarientDto, Product product){
		return productVarientDto.stream().map(productVarientsDto ->{
				ProductVarient productvarient = new ProductVarient();
		if( null != productVarientsDto.getId() ) {
			productvarient.setId(productVarientsDto.getId());
		}
		productvarient.setColor(productVarientsDto.getColor());
		productvarient.setSize(productVarientsDto.getSize());
		productvarient.setStockQuantity(productVarientsDto.getStockQuantity());
		productvarient.setProduct(product);
		return productvarient;
		}).collect(Collectors.toList());
	}
	
	public List<ProductDto> getProductDtos(List<Product> products){
		return products.stream().map(
				this::mapProductToDto
				).toList();
				
	}
	
	public ProductDto mapProductToDto(Product product) {
		return ProductDto.builder()
				.id(product.getId())
				.brand(product.getBrand())
				.name(product.getName())
				.price(product.getPrice())
				.isNewArrival(product.isNewArrival())
				.rating(product.getRating())
				.description(product.getDescription())
				.slug(product.getSlug())
				.thumbnail(getProductThumbnail(product.getResources())).build();
		
				
				
	}
	
	private String getProductThumbnail(List<Resources> resources) {
        return resources.stream().filter(Resources::getIsPrimary).findFirst().orElse(null).getUrl();
    }
	
	public List<ProductVarientDto> mapProductVariantListToDto(List<ProductVarient> productVariants) {
	       return productVariants.stream().map(this::mapProductVariantDto).toList();
	    }

	
	private ProductVarientDto mapProductVariantDto(ProductVarient productVariant) {
        return ProductVarientDto.builder()
                .color(productVariant.getColor())
                .id(productVariant.getId())
                .size(productVariant.getSize())
                .stockQuantity(productVariant.getStockQuantity())
                .build();
    }
	
	
	public List<ProductResourceDto> mapProductResourcesListDto(List<Resources> resources) {
        return resources.stream().map(this::mapResourceToDto).toList();
    }
	
	
	 private ProductResourceDto mapResourceToDto(Resources resources) {
	        return ProductResourceDto.builder()
	                .id(resources.getId())
	                .url(resources.getUrl())
	                .name(resources.getName())
	                .isPrimary(resources.getIsPrimary())
	                .type(resources.getType())
	                .build();
	    }
	
	
	
	
	
	
	
	
	
	
}
