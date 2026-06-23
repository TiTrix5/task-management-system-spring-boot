package com.example.taskmanagement.controller;

import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.dto.TaskResponse;
import com.example.taskmanagement.entity.Priority;
import com.example.taskmanagement.entity.Status;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.mapper.TaskMapper;
import com.example.taskmanagement.security.UserDetailsImpl;
import com.example.taskmanagement.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAllTasks(
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Priority priority,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<Task> tasks = taskService.getTasksForUser(userDetails.getId(), status, priority);
        List<TaskResponse> responses = tasks.stream()
                .map(taskMapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Task task = taskService.getTaskByIdForUser(id, userDetails.getId());
        return ResponseEntity.ok(taskMapper.toResponse(task));
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Task createdTask = taskService.createTask(request, userDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(taskMapper.toResponse(createdTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID id,
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Task updatedTask = taskService.updateTask(id, request, userDetails.getId());
        return ResponseEntity.ok(taskMapper.toResponse(updatedTask));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        taskService.deleteTask(id, userDetails.getId());
        return ResponseEntity.noContent().build();
    }
}