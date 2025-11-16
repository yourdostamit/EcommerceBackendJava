package com.ecommerce;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class EcommerceProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceProjectApplication.class, args);
        System.out.println("The ecommerce app is running...");
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Collections.singletonList("*")); // Allow all origins
        config.setAllowedHeaders(Arrays.asList(
                "Origin", "Content-Type", "Accept", "responseType",
                "Authorization", "x-authorization", "content-range", "range"
        ));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        config.setExposedHeaders(Arrays.asList(
                "X-Total-Count", "content-range", "Content-Type", "Accept", "X-Requested-With", "remember-me"
        ));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
