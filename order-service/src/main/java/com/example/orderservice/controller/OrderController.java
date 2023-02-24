package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDto;
import com.example.orderservice.dto.SaveOrderDto;
import com.example.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{user_id}/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrderAllByUserId(@PathVariable("user_id") String userId) {
        return orderService.findOrderAllByUserId(userId);
    }

    @PostMapping("/{user_id}/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@PathVariable("user_id") String userId, @RequestBody SaveOrderDto saveOrderDto) {
        return orderService.createOrder(userId, saveOrderDto);

    }
}
