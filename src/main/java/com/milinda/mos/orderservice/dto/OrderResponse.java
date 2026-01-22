package com.milinda.mos.orderservice.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderResponse {

    private Long id;
    private Long userId;
    private BigDecimal totalAmount;
    private List<OrderItemDto> items;

    public static class OrderItemDto {
        private Long productId;
        private Integer quantity;
        private BigDecimal price;

        public OrderItemDto() {
        }

        public OrderItemDto(Long productId, Integer quantity, BigDecimal price) {
            this.productId = productId;
            this.quantity = quantity;
            this.price = price;
        }

        public Long getProductId() {
            return productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }

    public OrderResponse() {
    }

    public OrderResponse(Long id, Long userId, BigDecimal totalAmount, List<OrderItemDto> items) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}
