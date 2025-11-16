package com.ecommerce.service;

import com.ecommerce.entity.Category;

import com.ecommerce.entity.CategoryType;
import com.ecommerce.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Repositries.CategoryRepository;
import com.ecommerce.dto.CategoryDto;
import com.ecommerce.dto.CategoryTypeDto;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	
	
	
	public Category getCategory(UUID categoryId) {
		Optional<Category> category= categoryRepository.findById(categoryId);
		return category.orElse(null);
	}
	
	public Category createCategory(CategoryDto categoryDto ) {
		Category category = mapToEntity(categoryDto);
		return categoryRepository.save(category);
	
	}
	
	private Category mapToEntity(CategoryDto categoryDto) {
		Category category= Category.builder()
				.code(categoryDto.getCode())
				.name(categoryDto.getName())
				.description(categoryDto.getDescription())
				.build();
		
		if(null != categoryDto.getCategoryTypes()) {
			List<CategoryType> categoryTypes = mapToCategoryTypeList(categoryDto.getCategoryTypes(),category);
			category.setCategoryType(categoryTypes);
		}
		return category;
	}
	
	private List<CategoryType>  mapToCategoryTypeList(List<CategoryTypeDto> categoryTypeList, Category category){
		
		return categoryTypeList.stream().map(categoryTypeDto -> {
			CategoryType categoryType = new CategoryType();
			categoryType.setCode(categoryTypeDto.getCode());
			categoryType.setName(categoryTypeDto.getName());
			categoryType.setDescription(categoryTypeDto.getDescription());
			categoryType.setCategory(category);
			return categoryType;
		}).collect(Collectors.toList());
	}
	
	public List<Category> getAllCategory(){
		return categoryRepository.findAll();
	}
	
	 public void deleteCategory(UUID categoryId) {
	        categoryRepository.deleteById(categoryId);
	    }
	 
	
	
	 
//	 public Category updatecateory(CategoryDto categoryDto, UUID categoryId) {
//
//		 
//		 Category category = categoryRepository.findById(categoryId).orElseThrow(()->
//		 new ResourceNotFoundException("category not found with id "+ categorydto.getId()));
//		 
//		 if(null != categoryDto.getName()) {
//			 category.setName(categoryDto.getName());
//		 }
//		 
//		 if(null != categoryDto.getCode()) {
//			 category.setCode(categoryDto.getCode());
//		 }
//		 
//		 if(null != categoryDto.getDescription()) {
//			 category.setDescription(categoryDto.getDescription());
//		 }
//		 
//		 List<CategoryType> existing = category.getCategoryType();
//		 
//		 List<CategoryType> list = new ArrayList<CategoryType>();
//		 
//		 if(categoryDto.getCategoryTypes() != null) {
//			 categoryDto.getCategoryTypes().forEach(categoryTypeDto->{
//			 if(null != categoryTypeDto.getId()) {
//				 Optional<CategoryType> categoryType= existing.stream().filter(t->
//				 t.getId().equals(categoryTypeDto.getId())
//						 ).findFirst();
//				 
//				 CategoryType categoryType1 = categoryType.get();
//				 
//				 categoryType1.setCode(categoryTypeDto.getCode());
//				 categoryType1.setName(categoryTypeDto.setName());
//				 categoryType1.setDescription(categoryTypeDto.setDescription());
//				 
//				 list.add(categoryType1);
//			 } else {
// CategoryType categoryType = categoryType.get();
//				 
//				 categoryType.setCode(categoryTypeDto.getCode());
//				 categoryType.setName(categoryTypeDto.setName());
//				 categoryType.setDescription(categoryTypeDto.setDescription());
//				 
//				 list.add(categoryType); 
//			 }
//			 category.setCategoryTypes(list);
//
//		        return  categoryRepository.save(category);
//			 }
//		 }
//		 
//	
//	 }
//	 }


	
	 public Category updateCategory(CategoryDto categoryDto, UUID categoryId) {
		    Category category = categoryRepository.findById(categoryId)
		        .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + categoryId));

		    if (categoryDto.getName() != null) {
		        category.setName(categoryDto.getName());
		    }
		    if (categoryDto.getCode() != null) {
		        category.setCode(categoryDto.getCode());
		    }
		    if (categoryDto.getDescription() != null) {
		        category.setDescription(categoryDto.getDescription());
		    }

		    List<CategoryType> existing = category.getCategoryType();
		    List<CategoryType> list = new ArrayList<>();

		    if (categoryDto.getCategoryTypes() != null) {
		        categoryDto.getCategoryTypes().forEach(categoryTypeDto -> {
		            if (categoryTypeDto.getId() != null) {
		                // update existing category type
		                Optional<CategoryType> categoryTypeOpt = existing.stream()
		                        .filter(t -> t.getId().equals(categoryTypeDto.getId()))
		                        .findFirst();

		                if (categoryTypeOpt.isPresent()) {
		                    CategoryType categoryType1 = categoryTypeOpt.get();
		                    categoryType1.setCode(categoryTypeDto.getCode());
		                    categoryType1.setName(categoryTypeDto.getName());
		                    categoryType1.setDescription(categoryTypeDto.getDescription());
		                    list.add(categoryType1);
		                }
		            } else {
		                // add new category type
		                CategoryType categoryType = new CategoryType();
		                categoryType.setCode(categoryTypeDto.getCode());
		                categoryType.setName(categoryTypeDto.getName());
		                categoryType.setDescription(categoryTypeDto.getDescription());
		                categoryType.setCategory(category);
		                list.add(categoryType);
		            }
		        });

		        category.setCategoryType(list);
		    }

		    return categoryRepository.save(category);
		}
	
	
	
	
	
	
	
	
	
	
}
