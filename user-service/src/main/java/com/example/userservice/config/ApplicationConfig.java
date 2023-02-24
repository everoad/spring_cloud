package com.example.userservice.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.format.DateTimeFormatter;

@Configuration
public class ApplicationConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer(@Value("${spring.mvc.format.date}") String dateFormat,
                                                                @Value("${spring.mvc.format.date-time}") String dateTimeFormat) {
        return builder -> {
            // 알 수 없는 변수는 무시
            builder.failOnUnknownProperties(false);
            // 값이 null인 경우 필드 삭제
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            // DateFormat
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormat);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
            // Request Body
            builder.deserializers(
                    new LocalDateDeserializer(dateFormatter),
                    new LocalDateTimeDeserializer(dateTimeFormatter)
            );
            // Response Body
            builder.serializers(
                    new LocalDateSerializer(dateFormatter),
                    new LocalDateTimeSerializer(dateTimeFormatter)
            );
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
