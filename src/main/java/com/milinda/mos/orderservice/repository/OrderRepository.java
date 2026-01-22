package com.milinda.mos.orderservice.repository;

import com.milinda.mos.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
