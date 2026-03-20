package com.scheduler.repository;

import com.scheduler.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Handles database operations for Task entities.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}