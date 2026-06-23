package com.example.taskmanagement.dto;

import com.example.taskmanagement.entity.Priority;
import com.example.taskmanagement.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private UUID id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private UUID userId;
}