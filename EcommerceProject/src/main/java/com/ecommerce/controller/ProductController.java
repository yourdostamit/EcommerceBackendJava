package com.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.ecommerce.Repositries.ProductRepository;
import com.ecommerce.dto.ProductDto;
import com.ecommerce.entity.Product;
import com.ecommerce.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;


@RestController
public class ProductController {



	@Autowired
	private ProductService productServie;

   
	
	@PostMapping("/api/products")
	public ResponseEntity<Product> saveProduct(@RequestBody ProductDto productDto){
		System.out.println("the product dto is " + productDto.toString());
		Product savedProduct= productServie.addProduct(productDto);
		return new ResponseEntity<Product>(savedProduct,HttpStatus.OK);
	}
	
	@GetMapping("/api/products")
	public ResponseEntity<List<ProductDto>> getallProduct(@RequestParam(required = false,name="categoryId", value = "categoryId") UUID categoryId, @RequestParam(required = false,name="typeId",value="typeId") UUID typeId, @RequestParam(required = false) String slug,HttpServletResponse response ){
		
		List<ProductDto> productList = new ArrayList<ProductDto>();
		if(StringUtils.isNotBlank(slug)) {
			ProductDto productDto = productServie.getProductBySlug(slug);
			productList.add(productDto);
		}else {
			productList = productServie.getAllProducts(categoryId, typeId);
		}
		response.setHeader("Content-Range", String.valueOf(productList.size()));
		return new ResponseEntity<>(productList,HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id){
		ProductDto productDto = productServie.getProductById(id);
		return new ResponseEntity<ProductDto>(productDto,HttpStatus.OK);
		
	}
	
	
	@PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDto productDto,@PathVariable UUID id){
        Product product = productServie.updateProduct(productDto,id);
        return new ResponseEntity<>(product,HttpStatus.OK);
    }
	
//	public Product fetchProductById(UUID id) throws Exception {
//		return null;
//	}
	
	
	
	
	
	
	
	
	
}
