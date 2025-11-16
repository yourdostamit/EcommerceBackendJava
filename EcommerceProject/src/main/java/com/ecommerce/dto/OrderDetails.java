package com.ecommerce.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.ecommerce.auth.entity.Adresses;
import com.ecommerce.entity.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails {

	  private UUID id;
	    private Date orderDate;
	    private Adresses address;
	    private Double totalAmount;
	    private OrderStatus orderStatus;
	    private String shipmentNumber;
	    private Date expectedDeliveryDate;
	    private List<OrderItemDetail> orderItemList;
	
}
