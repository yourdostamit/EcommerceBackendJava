package com.ecommerce.specification;

import java.util.UUID;

import org.springframework.data.jpa.domain.Specification;

import com.ecommerce.entity.Product;

import jakarta.persistence.criteria.CriteriaBuilder;

public class ProductSpecification {

	public static Specification<Product> hasCategoryId(UUID categoryId){
		return (root,query,CriteriaBuilder) -> CriteriaBuilder.equal(root.get("category").get("id"), categoryId);
	}
	
	public static Specification<Product> hascategoryTypeId(UUID typeId){
		
		return (root,query,CriteriaBuilder) -> CriteriaBuilder.equal(root.get("categoryType").get("id"), typeId);
	}
}
