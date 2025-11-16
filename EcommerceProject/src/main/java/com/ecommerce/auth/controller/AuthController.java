package com.ecommerce.auth.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.config.JwtTokenHelper;
import com.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.auth.dto.RegistationResponse;
import com.ecommerce.auth.dto.RegistrationRequest;
import com.ecommerce.auth.dto.UserToken;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.service.RegistrationService;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager ;
	
	@Autowired
	UserDetailsService UserDetailsService;
	
	@Autowired
	RegistrationService registrationService ;
	
	@Autowired
	JwtTokenHelper jwtTokenHelper ;
	
	
	
	@PostMapping("/login")
	public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest ){
		try {
			Authentication authentication = UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUserName(), loginRequest.getPassword());
			
			Authentication authenticationResponse=this.authenticationManager.authenticate(authentication);
			
			if(authenticationResponse.isAuthenticated()) {
				User user= (User) authenticationResponse.getPrincipal();
				System.out.println("the user details for the auth is "+ user.toString());
				if(!user.isEnabled()) {
					return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
				}
				String token= jwtTokenHelper.generateToken(user.getEmail());
				UserToken userToken= UserToken.builder().token(token).build();
				
				return new ResponseEntity<>(userToken,HttpStatus.OK);
			}
			
		} catch (BadCredentialsException e) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/register")
	public ResponseEntity<RegistationResponse> register(@RequestBody RegistrationRequest request ){
		
		
		RegistationResponse registationResponse= registrationService.createUser(request);
		
		return new ResponseEntity<>(registationResponse, registationResponse.getCode()== 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/verify")
	public ResponseEntity<?> verifyCode(@RequestBody Map<String,String> map){
		String userName= map.get("userName");
		String code= map.get("code");
		User user= (User) UserDetailsService.loadUserByUsername(userName);
		if(null !=user && user.getVerificationCode().equals(code)) {
			registrationService.verifyUser(userName);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
