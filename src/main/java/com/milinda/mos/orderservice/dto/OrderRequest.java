package com.milinda.mos.orderservice.dto;

import java.util.List;

public class OrderRequest {

    private Long userId;
    private List<OrderItemRequest> items;

    public OrderRequest() {
    }

    public OrderRequest(Long userId, List<OrderItemRequest> items) {
        this.userId = userId;
        this.items = items;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}
