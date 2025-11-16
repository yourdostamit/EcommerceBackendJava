package com.ecommerce.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repositries.UserDetailRepository;

@Service
public class OAuth2Service {

	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private  UserDetailRepository  userDetailRepository;
	
	
	public User getUser(String userName) {
		return userDetailRepository.findByEmail(userName);
		
	}
	public User createUser(OAuth2User oAuth2user,String provider) {
		
		String firstName= oAuth2user.getAttribute("given_name");
		String lastName= oAuth2user.getAttribute("family_name");
		String email= oAuth2user.getAttribute("email");
		
		User user= User.builder()
				.firstName(firstName)
				.LastName(lastName)
				.email(email)
				.provider(provider)
				.enabled(true)
				.authorities(authorityService.getUserAuthority())
				.build();
		return userDetailRepository.save(user);
		
		
		
		
		
		
		
		
		
	}
	
}
