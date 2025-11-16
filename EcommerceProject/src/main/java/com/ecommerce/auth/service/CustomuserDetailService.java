package com.ecommerce.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repositries.UserDetailRepository;

@Service
public class CustomuserDetailService implements UserDetailsService {

	@Autowired
	private UserDetailRepository userDetailRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDetailRepository.findByEmail(username);
		if(null == user) {
			throw new UsernameNotFoundException("user Not found with username" + username);
			
		}
		return user;
	}

}
