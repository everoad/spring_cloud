package com.example.orderservice.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Order {
    private String orderId;
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private String userId;
    private LocalDateTime createdDate;

    public void calculateTotalPrice() {
        this.totalPrice = this.qty * this.unitPrice;
    }
}
