package com.ecommerce.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ecommerce.auth.entity.User;
import com.ecommerce.entity.Order;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCancelParams;
import com.stripe.param.PaymentIntentCreateParams;

@Component
public class PaymentIntentService {

	public Map<String, String> createPaymentIntent(Order order) throws Exception{
		User user= order.getUser();
		Map<String, String> metaData = new HashMap<String, String>();
		metaData.put("orderId", order.getId().toString());
		PaymentIntentCreateParams paymentIntentCancelParams = PaymentIntentCreateParams
				.builder()
				.setAmount((long) (order.getTotalAmount() * 100 * 80))
				.setCurrency("inr")
				.setDescription("Test Payment project -1")
				.setAutomaticPaymentMethods(
						PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true)
						.build()
						).build();
		
		
		PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentCancelParams);
		Map<String, String> map = new HashMap<String, String>();
		map.put("client_secret", paymentIntent.getClientSecret());
		return map;
		
		
	}
}
