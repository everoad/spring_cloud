package com.example.userservice.mapper;

import com.example.userservice.dto.SaveUserDto;
import com.example.userservice.domain.User;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(User userDto) {
        return UserEntity.builder()
                .id(userDto.getUserId())
                .email(userDto.getEmail())
                .pwd(userDto.getPwd())
                .name(userDto.getName())
                .build();
    }

    public UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .userId(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .build();
    }

    public User toDto(SaveUserDto saveUserDto) {
        return User.builder()
                .email(saveUserDto.getEmail())
                .pwd(saveUserDto.getPwd())
                .name(saveUserDto.getName())
                .build();
    }
}
