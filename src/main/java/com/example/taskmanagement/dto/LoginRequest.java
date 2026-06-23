package com.example.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Логин (username или email) обязателен")
    private String login;

    @NotBlank(message = "Пароль обязателен")
    private String password;
}