package com.ecommerce.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;

@Service
public interface ProductService {

	public Product addProduct(ProductDto productDto);
	
	public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId);
	
	public ProductDto getProductBySlug(String slug);
	
	public ProductDto getProductById(UUID id);
	public Product fetchProductById(UUID id) throws Exception ;
	public Product updateProduct(ProductDto productDto, UUID id);
}
