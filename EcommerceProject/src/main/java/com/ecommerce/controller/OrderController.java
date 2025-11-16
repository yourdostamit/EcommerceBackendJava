package com.ecommerce.controller;

import java.security.Principal;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.dto.OrderDetails;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.service.OrderService;


@RestController
@RequestMapping("/api/order")

public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, Principal princpal) throws Exception {
		OrderResponse orderResponse = orderService.createOrder(orderRequest, princpal);
		return new ResponseEntity<>(orderResponse,HttpStatus.OK);
		
	}
	
	@PostMapping("/update-payment")
    public ResponseEntity<?> updatePaymentStatus(@RequestBody Map<String,String> request){
        Map<String,String> response = orderService.updateStatus(request.get("paymentIntent"),request.get("status"));
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable UUID id,Principal principal){
        orderService.cancelOrder(id,principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDetails>> getOrderByUser(Principal principal) {
        List<OrderDetails> orders = orderService.getOrderByUser(principal.getName());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
	
}
