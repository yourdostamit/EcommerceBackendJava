package com.ecommerce.Repositries;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product>{

	Product findBySlug(String slug);
	ProductDto getProductById(UUID id);
	
	
	
}
