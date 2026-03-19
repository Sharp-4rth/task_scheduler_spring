package com.example.scheduler.repository;

import com.example.scheduler.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Handles database operations for Task entities.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}