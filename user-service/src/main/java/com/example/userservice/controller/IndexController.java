package com.example.userservice.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final Environment env;

    @GetMapping("/health_check")
    @Timed(value = "users.status", longTask = true)
    public String status() {
        return String.format(
                "It's Working in Catalog Service on PORT %s, EXPIRATION TIME %s, SECRET %s",
                env.getProperty("local.server.port"),
                env.getProperty("token.expiration_time"),
                env.getProperty("token.secret")
        );
    }

    @GetMapping("/welcome")
    @Timed(value = "users.welcome", longTask = true)
    public String welcome() {
        return "welcome";
    }

}
