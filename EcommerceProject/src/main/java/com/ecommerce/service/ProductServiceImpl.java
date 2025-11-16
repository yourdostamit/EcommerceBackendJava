package com.ecommerce.service;

import java.util.List;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ecommerce.Repositries.ProductRepository;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.mapper.ProductMapper;
import com.ecommerce.specification.ProductSpecification;

@Service
public class ProductServiceImpl implements ProductService {

   

	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private ProductMapper productMapper;


	
	@Override
	public Product addProduct(ProductDto productDto) {
		Product product = productMapper.mapToProductEntity(productDto);
		return productRepo.save(product);
	}

	@Override
	public  List<ProductDto> getAllProducts(UUID categoryId, UUID typeId) {
		
		Specification<Product> productSpecification = Specification.where(null);
		
		if(null != categoryId) {
		    productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
		}

		if(null != typeId) {
		    productSpecification = productSpecification.and(ProductSpecification.hascategoryTypeId(typeId));
		}		
		
		List<Product> productList= productRepo.findAll(productSpecification);
		return  productMapper.getProductDtos(productList);
	}

	@Override
	public ProductDto getProductBySlug(String slug) {
		
		Product product = productRepo.findBySlug(slug);
		if(null == product) {
			throw new ResourceNotFoundException("product not found");
			
		}
		
		ProductDto productDto= productMapper.mapProductToDto(product);
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setCategoryTypeId(product.getCategoryType().getId());
		productDto.setVarients(productMapper.mapProductVariantListToDto(product.getProductVarients()));
		productDto.setProductResource(productMapper.mapProductResourcesListDto(product.getResources()));
		return productDto;	
	}

	@Override
	public ProductDto getProductById(UUID id) {
		Product product = productRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("product not found !"));
		
		ProductDto productDto = productMapper.mapProductToDto(product);
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setCategoryTypeId(product.getCategoryType().getId());
		productDto.setVarients(productMapper.mapProductVariantListToDto(product.getProductVarients()));
		productDto.setProductResource(productMapper.mapProductResourcesListDto(product.getResources()));
		return productDto;
		


	
	}

	@Override
	public Product updateProduct(ProductDto productDto, UUID id) {
		Product product= productRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("product not found !"));
		productDto.setId(product.getId());
		return productRepo.save(productMapper.mapToProductEntity(productDto));
	}
	
	 @Override
	    public Product fetchProductById(UUID id) throws Exception {
	        return productRepo.findById(id).orElseThrow(BadRequestException::new);
	    }
	
	
	
} 
