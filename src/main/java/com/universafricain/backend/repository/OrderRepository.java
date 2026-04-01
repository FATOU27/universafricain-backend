package com.universafricain.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.universafricain.backend.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(Order.Status status);

    List<Order> findByCustomerEmail(String email);
}