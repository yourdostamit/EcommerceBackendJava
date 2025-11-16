package com.ecommerce.service;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.ecommerce.Repositries.OrderRepository;
import com.ecommerce.auth.entity.Adresses;
import com.ecommerce.auth.entity.User;
import com.ecommerce.dto.OrderDetails;
import com.ecommerce.dto.OrderItemDetail;
import com.ecommerce.dto.OrderRequest;
import com.ecommerce.dto.OrderResponse;
import com.ecommerce.entity.Order;
import com.ecommerce.entity.OrderItem;
import com.ecommerce.entity.OrderStatus;
import com.ecommerce.entity.Payment;
import com.ecommerce.entity.PaymentStatus;
import com.ecommerce.entity.Product;
import com.stripe.model.PaymentIntent;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private ProductService productService;

	@Autowired
   private  PaymentIntentService paymentIntentService;
   


    

	
	@Transactional
	public OrderResponse createOrder( OrderRequest orderRequest, Principal principal) throws Exception {
		System.out.println("the orderRequest coming is " + orderRequest.toString());
		User user = (User) userDetailsService.loadUserByUsername(principal.getName());
		Adresses address= user.getAddressList().stream().filter(adress1-> orderRequest.getAddressId().equals(adress1.getId())).findFirst().orElseThrow(BadRequestException::new);
		System.out.println("the orderRequest coming adress Is " + address.toString());
		Order order= Order.builder()
				.user(user)
				.address(address)
				.orderDate(orderRequest.getOrderdate())
				.totalAmount(orderRequest.getTotalAmount())
				.discount(orderRequest.getDiscount())
				.exceptedDeleveryDate(orderRequest.getExpectedDeliveryDate())
				.orderStatus(OrderStatus.PENDING)
				.paymentMethod(orderRequest.getPaymentMethod())
				.build();
		
		List<OrderItem> orderItems = orderRequest.getOrderItemRequests().stream()
				.map(orderItemRequest ->{
					try {
						Product product= productService.fetchProductById(orderItemRequest.getProductId());
						OrderItem orderItem = OrderItem.builder()
								.product(product)
								.productVarientId(orderItemRequest.getProductVarientId())
								.quantity(orderItemRequest.getQuantity())
								.order(order)
								.build();
						return orderItem;
					} catch (Exception e) {
						 throw new RuntimeException(e);
					}
				}).toList();
		
		order.setOrderItemList(orderItems);
		Payment payment= new Payment();
		payment.setPaymentStatus(PaymentStatus.PENDING);
		payment.setPaymentDate(new Date());
		payment.setOrder(order);
		payment.setAmount(order.getTotalAmount());
		payment.setPaymentMethod(order.getPaymentMethod());
		order.setPayment(payment);
		Order savedOrder= orderRepository.save(order);
		
		OrderResponse orderResponse = OrderResponse.builder()
				.paymentMethod(orderRequest.getPaymentMethod())
				.orderId(savedOrder.getId())
				.build();
		if(Objects.equals(orderRequest.getPaymentMethod(), "CARD" )) {
			orderResponse.setCredentials(paymentIntentService.createPaymentIntent( order ));
		}
		return orderResponse;
	}
	
	public Map<String, String> updateStatus(String paymentIntentId, String status){
		try {
			PaymentIntent paymentIntent = PaymentIntent.retrieve(status);
			 if (paymentIntent != null && paymentIntent.getStatus().equals("succeeded")) {
	               String orderId = paymentIntent.getMetadata().get("orderId") ;
	               Order order= orderRepository.findById(UUID.fromString(orderId)).orElseThrow(BadRequestException::new);
	               Payment payment = order.getPayment();
	               payment.setPaymentStatus(PaymentStatus.COMPLETED);
	                payment.setPaymentMethod(paymentIntent.getPaymentMethod());
	                order.setPaymentMethod(paymentIntent.getPaymentMethod());
	                order.setOrderStatus(OrderStatus.IN_PROGRESS);
	                order.setPayment(payment);
	                Order savedOrder = orderRepository.save(order);
	                Map<String,String> map = new HashMap<>();
	                map.put("orderId", String.valueOf(savedOrder.getId()));
	                return map;
	            }
	            else{
	                throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
	            }
		} catch (Exception e) {
			throw new IllegalArgumentException("PaymentIntent not found or missing metadata");
        }
		
		
	
	}
	
	
//	public List<OrderDetails> getOrderByUser(String name){
//		User user = (User) userDetailsService.loadUserByUsername(name);
//		
//		List<Order> orders= orderRepository.findByUser(user);
//		return orders.stream().map(order -> {
//			return OrderDetails.builder()
//					.id(order.getId())
//					.orderDate(order.getDate())
//					.shipmentNumber(order.getShipmentTrackingNumber())
//					.address(order.getAddress())
//					.totalAmount(order.getTotalAmount())
//					.orderItemList(getItemsdetails(order.getOrderItemList()))
//					.expectedDeliveryDate(order.getExceptedDeleveryDate())
//					.build();
//		}).toList();
//	}
	
	
//	public List<OrderDetails> getOrderByUser(String name) {
//	    User user = (User) userDetailsService.loadUserByUsername(name);
//	    List<Order> orders = orderRepository.findByUser(user);
//	    return orders.stream().map(order -> {
//	        return OrderDetails.builder()
//	                .id(order.getId())
//	                .orderDate(order.getOrderDate())
//	                .orderDate(order.getDate())
//	                .shipmentNumber(order.getShipmentTrackingNumber())
//	                .address(order.getAddress())
//	                .totalAmount(order.getTotalAmount())
//	                .orderItemList(getItemDetails(order.getOrderItemList())) // Fixed: Added closing ) and comma
//	                .expectedDeliveryDate(order.getExceptedDeleveryDate())
//	                .build();
//	    }).toList();
//	}
	
	private List<OrderItemDetail> getItemDetails(List<OrderItem> orderItemList){
		return orderItemList.stream().map(orderItem->{
			return OrderItemDetail.builder()
					.id(orderItem.getId())
					.itemPrice(orderItem.getItemPrice())
					.product(orderItem.getProduct())
					.productVariantId(orderItem.getProductVarientId())
					.quantity(orderItem.getQuantity())
					.build();
		}).toList();
	}
	
	
	public void cancelOrder(UUID id, Principal principal) {
		User user= (User) userDetailsService.loadUserByUsername(principal.getName());
		Order order= orderRepository.findById(id).get();
		
		if(null != order && order.getUser().getId().equals(user.getId())) {
			order.setOrderStatus(OrderStatus.CANCELED);
			orderRepository.save(order);
		} else {
			new RuntimeException("Invalid request");
		}
	}
	
	
}
