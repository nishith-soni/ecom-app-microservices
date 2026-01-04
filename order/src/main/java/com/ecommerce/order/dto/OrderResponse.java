package com.ecommerce.order.dto;

import com.ecommerce.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private List<OrderItemsDTO> orderItemsDTOS;
    private LocalDateTime createdAt;

    public OrderResponse() {
    }

    public OrderResponse(Long id, BigDecimal totalAmount, OrderStatus orderStatus, List<OrderItemsDTO> orderItemsDTOS, LocalDateTime createdAt) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.orderItemsDTOS = orderItemsDTOS;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public List<OrderItemsDTO> getOrderItemsDTOS() {
        return orderItemsDTOS;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
