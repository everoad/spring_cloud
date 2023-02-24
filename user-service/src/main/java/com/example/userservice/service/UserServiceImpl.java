package com.example.userservice.service;

import com.example.userservice.dto.OrderDto;
import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.UserEntity;
import com.example.userservice.domain.User;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.crypto.generators.BCrypt;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(entity -> {
                    return new org.springframework.security.core.userdetails.User(
                            entity.getEmail(),
                            entity.getPwd(),
                            true, true, true, true,
                            new ArrayList<>()
                    );
                })
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public List<UserDto> getUserAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto).toList();
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserDto userDto = userRepository.findById(userId)
                .map(userMapper::toDto)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderDto> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public UserDto createUser(User userDto)  {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setPwd(passwordEncoder.encode(userDto.getPwd()));
        UserEntity userEntity = userMapper.toEntity(userDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userMapper.toDto(savedUserEntity);
    }
}
