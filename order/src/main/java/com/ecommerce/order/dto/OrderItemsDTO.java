package com.ecommerce.order.dto;

import java.math.BigDecimal;


public class OrderItemsDTO {
    private Long id;
    private Long productID;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subtotal;

    public OrderItemsDTO(Long id, Long productID, Integer quantity, BigDecimal price, BigDecimal subtotal) {
        this.id = id;
        this.productID = productID;
        this.quantity = quantity;
        this.price = price;
        this.subtotal = subtotal;
    }

    public OrderItemsDTO() {
    }

    public Long getId() {
        return id;
    }

    public Long getProductID() {
        return productID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
