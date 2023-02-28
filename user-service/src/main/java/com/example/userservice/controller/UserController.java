package com.example.userservice.controller;

import com.example.userservice.domain.User;
import com.example.userservice.dto.SaveUserDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Timed(value = "users.list", longTask = true)
    public List<UserDto> getUserAll() {
        return userService.getUserAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody SaveUserDto saveUserDto) {
        User userDto = userMapper.toDto(saveUserDto);
        return userService.createUser(userDto);
    }

    @GetMapping("/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserByUserId(@PathVariable("user_id") String userId) {
        return userService.getUserByUserId(userId);
    }

}
