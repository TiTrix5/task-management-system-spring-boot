package com.example.taskmanagement.dto;

import com.example.taskmanagement.entity.Priority;
import com.example.taskmanagement.entity.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskRequest {

    @NotBlank(message = "Название задачи обязательно")
    private String title;

    private String description;

    @NotNull(message = "Статус обязателен")
    private Status status;

    @NotNull(message = "Приоритет обязателен")
    private Priority priority;
}