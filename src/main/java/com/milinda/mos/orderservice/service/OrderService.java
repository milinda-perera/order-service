package com.milinda.mos.orderservice.service;

import com.milinda.mos.orderservice.dto.OrderItemRequest;
import com.milinda.mos.orderservice.dto.OrderRequest;
import com.milinda.mos.orderservice.dto.OrderResponse;
import com.milinda.mos.orderservice.dto.ProductResponse;
import com.milinda.mos.orderservice.model.Order;
import com.milinda.mos.orderservice.model.OrderItem;
import com.milinda.mos.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final String userServiceBaseUrl;
    private final String productServiceBaseUrl;

    public OrderService(OrderRepository orderRepository,
                        RestTemplate restTemplate,
                        @Value("${external.user-service.base-url}") String userServiceBaseUrl,
                        @Value("${external.product-service.base-url}") String productServiceBaseUrl) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
        this.userServiceBaseUrl = userServiceBaseUrl;
        this.productServiceBaseUrl = productServiceBaseUrl;
    }

    public OrderResponse createOrder(OrderRequest request) {
        String userUrl = userServiceBaseUrl + "/users/" + request.getUserId();
        restTemplate.getForObject(userUrl, Object.class);

        BigDecimal total = BigDecimal.ZERO;
        Order order = new Order();
        order.setUserId(request.getUserId());

        for (OrderItemRequest itemReq : request.getItems()) {
            String productUrl = productServiceBaseUrl + "/products/" + itemReq.getProductId();
            ProductResponse product = restTemplate.getForObject(productUrl, ProductResponse.class);

            if (product == null) {
                throw new IllegalArgumentException("Product not found: " + itemReq.getProductId());
            }

            BigDecimal price = product.getPrice();
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            total = total.add(lineTotal);

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(itemReq.getProductId());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(price);

            order.getItems().add(item);
        }

        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);

        List<OrderResponse.OrderItemDto> itemDtos = saved.getItems().stream()
                .map(i -> new OrderResponse.OrderItemDto(i.getProductId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList());

        return new OrderResponse(saved.getId(), saved.getUserId(), saved.getTotalAmount(), itemDtos);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));
        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {
        List<OrderResponse.OrderItemDto> items = order.getItems().stream()
                .map(i -> new OrderResponse.OrderItemDto(i.getProductId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList());
        return new OrderResponse(order.getId(), order.getUserId(), order.getTotalAmount(), items);
    }
}
