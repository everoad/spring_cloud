package com.example.orderservice.service;

import com.example.orderservice.domain.Order;
import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.SaveOrderDto;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.mapper.OrderMapper;
import com.example.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderDto createOrder(String userId, SaveOrderDto saveOrderDto) {
        Order order = orderMapper.toDomain(userId, saveOrderDto);
        order.calculateTotalPrice();
        OrderEntity orderEntity = orderMapper.toEntity(order);
        OrderEntity savedOrderEntity = orderRepository.save(orderEntity);
        return orderMapper.toDto(savedOrderEntity);
    }

    public List<OrderDto> findOrderAllByUserId(String userId) {
        return orderRepository.findAllByUserId(userId)
                .stream().map(orderMapper::toDto).toList();
    }
}
