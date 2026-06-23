package com.example.taskmanagement.repository;

import com.example.taskmanagement.entity.Priority;
import com.example.taskmanagement.entity.Status;
import com.example.taskmanagement.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    @Query("""
        SELECT t FROM Task t 
        WHERE t.user.id = :userId 
        AND (:status IS NULL OR t.status = :status) 
        AND (:priority IS NULL OR t.priority = :priority)
        ORDER BY t.priority DESC, t.status ASC
        """)
    List<Task> findByUserIdWithFilters(
            @Param("userId") UUID userId,
            @Param("status") Status status,
            @Param("priority") Priority priority
    );

    Optional<Task> findByIdAndUserId(UUID id, UUID userId);

    boolean existsByIdAndUserId(UUID id, UUID userId);
}