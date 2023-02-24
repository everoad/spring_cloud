package com.example.userservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdDate;
    private String orderId;
}
