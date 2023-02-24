package com.example.userservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserDto {
    private String userId;
    private String email;
    private String name;
    private List<OrderDto> orders;
}
