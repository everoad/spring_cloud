package com.example.userservice.dto;

import lombok.Data;

@Data
public class RequestLogin {
    private String email;
    private String pwd;
}
