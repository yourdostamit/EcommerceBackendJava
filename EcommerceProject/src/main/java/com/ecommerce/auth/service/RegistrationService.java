package com.ecommerce.auth.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

import com.ecommerce.auth.dto.RegistationResponse;
import com.ecommerce.auth.dto.RegistrationRequest;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.helper.VerificationCodeGernator;
import com.ecommerce.auth.repositries.UserDetailRepository;

@Service
public class RegistrationService {

	@Autowired
	private UserDetailRepository userDetailRepository ;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthorityService authorityService ;
	
	public RegistationResponse createUser(RegistrationRequest request) {
		
		User existing= userDetailRepository.findByEmail(request.getEmail());
		
		if(null != existing) {
			return RegistationResponse.builder().
					code(400)
					.message("Email already exists")
					.build();
			
		}
		
		try {
			User user= new User();
			user.setFirstName(request.getFirstName());
			user.setLastName(request.getLastName());
			user.setEmail(request.getEmail());
			user.setEnabled(false);
			user.setPassword(passwordEncoder.encode(request.getPassword()));
			user.setProvider("manual");
			String code= VerificationCodeGernator.generateCode();
			
			user.setVerificationCode(code);
			user.setAuthorities(authorityService.getUserAuthority());
			userDetailRepository.save(user);
			emailService.sendMail(user);
			
			return RegistationResponse.builder()
					.code(200)
					.message("user Created!")
					.build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw new ServerErrorException(e.getMessage(), e.getCause());
		}
		
		
	}
	
	public void verifyUser(String userName) {
		User user= userDetailRepository.findByEmail(userName);
		user.setEnabled(true);
		userDetailRepository.save(user);
	}
	
}
