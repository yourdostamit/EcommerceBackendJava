package com.ecommerce.controller;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.entity.Adresses;
import com.ecommerce.dto.AddressRequest;
import com.ecommerce.service.AddressService;



@RestController
@RequestMapping("/api/address")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/createAdress")
	public ResponseEntity<Adresses> createAddress(@RequestBody AddressRequest addressRequest, Principal princple){
		
		Adresses address = addressService.createAdress(addressRequest, princple);
		return new ResponseEntity<Adresses>(address,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteAdress(@PathVariable UUID id){
		addressService.deleteAddress(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	
}
