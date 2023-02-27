package com.example.userservice.config.security;

import com.example.userservice.dto.RequestLogin;
import com.example.userservice.dto.UserDto;
import com.example.userservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin requestLogin = objectMapper.readValue(request.getInputStream(), RequestLogin.class);
            log.info("Request login -> {}", requestLogin);
            Authentication token = new UsernamePasswordAuthenticationToken(
                    requestLogin.getEmail(), requestLogin.getPwd(), new ArrayList<>()
            );
            return getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.info("Login success!");
        User user = (User) authResult.getPrincipal();
        UserDto userDto = userService.getUserDetailsByEmail(user.getUsername());


        long tokenExpirationTime = Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration_time")));
        String tokenSecret = environment.getProperty("token.secret");
        assert tokenSecret != null;

        String token = Jwts.builder()
                .setSubject(userDto.getUserId())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS512, Base64.getEncoder().encode(tokenSecret.getBytes(StandardCharsets.UTF_8)))
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDto.getUserId());
//        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.info("Login fail!");
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
