package com.milinda.mos.orderservice.controller;

import com.milinda.mos.orderservice.dto.OrderRequest;
import com.milinda.mos.orderservice.dto.OrderResponse;
import com.milinda.mos.orderservice.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody OrderRequest request) {
        OrderResponse created = service.createOrder(request);
        return ResponseEntity
                .created(URI.create("/orders/" + created.getId()))
                .body(created);
    }

    @GetMapping
    public List<OrderResponse> getAll() {
        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        try {
            OrderResponse response = service.getOrderById(id);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
