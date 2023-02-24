package com.example.orderservice.mapper;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.SaveOrderDto;
import com.example.orderservice.entity.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class OrderMapper {

    public OrderDto toDto(OrderEntity orderEntity) {
        return OrderDto.builder()
                .orderId(orderEntity.getId())
                .productId(orderEntity.getProductId())
                .qty(orderEntity.getQty())
                .unitPrice(orderEntity.getUnitPrice())
                .totalPrice(orderEntity.getTotalPrice())
                .createdDate(orderEntity.getCreatedDate())
                .build();
    }

    public Order toDomain(String userId, SaveOrderDto orderDto) {
        return Order.builder()
                .userId(userId)
                .orderId(UUID.randomUUID().toString())
                .productId(orderDto.getProductId())
                .unitPrice(orderDto.getUnitPrice())
                .qty(orderDto.getQty())
                .build();
    }

    public OrderEntity toEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getOrderId())
                .userId(order.getUserId())
                .unitPrice(order.getUnitPrice())
                .totalPrice(order.getTotalPrice())
                .productId(order.getProductId())
                .qty(order.getQty())
                .build();
    }
}
