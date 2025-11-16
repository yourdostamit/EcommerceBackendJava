package com.ecommerce.service;

import java.security.Principal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.ecommerce.Repositries.AdressRepository;
import com.ecommerce.auth.entity.Adresses;
import com.ecommerce.auth.entity.User;
import com.ecommerce.dto.AddressRequest;

@Service
public class AddressService {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AdressRepository adressRepository;
	
	public Adresses createAdress(AddressRequest addressrequest, Principal princpal) {
		User user= (User) userDetailsService.loadUserByUsername(princpal.getName());
		Adresses address = Adresses.builder()
				.name(addressrequest.getName())
				.state(addressrequest.getState())
				.city(addressrequest.getCity())
				.street(addressrequest.getStreet())
				.ZipCode(addressrequest.getZipCode())
				.phoneNumber(addressrequest.getPhoneNumber())
				.user(user)
				.build();
		return adressRepository.save(address);
	}
	
	public void deleteAddress(UUID id) {
		adressRepository.deleteById(id);
	}
	
}
