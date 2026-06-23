package com.example.taskmanagement.service;

import com.example.taskmanagement.dto.TaskRequest;
import com.example.taskmanagement.entity.Priority;
import com.example.taskmanagement.entity.Status;
import com.example.taskmanagement.entity.Task;
import com.example.taskmanagement.entity.User;
import com.example.taskmanagement.exception.AccessDeniedException;
import com.example.taskmanagement.exception.ResourceNotFoundException;
import com.example.taskmanagement.mapper.TaskMapper;
import com.example.taskmanagement.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    public List<Task> getTasksForUser(UUID userId, Status status, Priority priority) {
        return taskRepository.findByUserIdWithFilters(userId, status, priority);
    }

    @Transactional(readOnly = true)
    public Task getTaskByIdForUser(UUID taskId, UUID userId) {
        return taskRepository.findByIdAndUserId(taskId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Задача не найдена или у вас нет доступа к ней"));
    }

    @Transactional
    public Task createTask(TaskRequest request, UUID userId) {
        User user = userService.getUserById(userId);

        Task task = taskMapper.toEntity(request);
        task.setUser(user);

        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(UUID taskId, TaskRequest request, UUID userId) {
        Task existingTask = getTaskByIdForUser(taskId, userId);

        taskMapper.updateEntityFromRequest(request, existingTask);
        existingTask.setUser(existingTask.getUser());

        return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTask(UUID taskId, UUID userId) {
        if (!taskRepository.existsByIdAndUserId(taskId, userId)) {
            throw new ResourceNotFoundException("Задача не найдена или у вас нет доступа к ней");
        }
        taskRepository.deleteById(taskId);
    }
}