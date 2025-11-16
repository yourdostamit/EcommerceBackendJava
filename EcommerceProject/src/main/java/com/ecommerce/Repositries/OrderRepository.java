package com.ecommerce.Repositries;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.auth.entity.User;
import com.ecommerce.entity.Order;

@Repository
public interface OrderRepository  extends JpaRepository<Order, UUID> {
List<Order> findByUser(User user);
}
