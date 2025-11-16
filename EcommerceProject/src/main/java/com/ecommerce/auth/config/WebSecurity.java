package com.ecommerce.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity {
	
	  @Autowired
	    private UserDetailsService userDetailsService;

	    @Autowired
	    private JwtTokenHelper jwtTokenHelper;

	private static final String[] publicApis= {
            "/api/auth/**"
    };

	@Bean
	public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests((authorize)-> authorize
				.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**","/api/auth/register","/api/auth/**").permitAll()
				.requestMatchers(HttpMethod.GET, "/api/products","/api/category").permitAll()
//				.requestMatchers("/api/products","/api/category").permitAll()
				.anyRequest().authenticated()
				
				)
		.oauth2Login((oauth2Login)-> oauth2Login.defaultSuccessUrl("/oauth2/success")
				.loginPage("/oauth2/authorization/google"))
		.addFilterBefore(new JWTAuthenticationFilter(jwtTokenHelper, userDetailsService),UsernamePasswordAuthenticationFilter.class);
		;
		return http.build();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		 daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
	}
	
	@Bean 
	public WebSecurityCustomizer  webSecurityCustomizer() { 
		return (web)-> web.ignoring().requestMatchers(publicApis);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
