package com.ecommerce.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController {
	
	

	@GetMapping("/")
	public String showGreet() {
		return "welcome to the shopping app";
	}
	
	
	
	
}
