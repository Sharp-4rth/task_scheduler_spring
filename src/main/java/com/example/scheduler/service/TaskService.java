package com.example.scheduler.service;

import com.example.scheduler.model.Task;
import com.example.scheduler.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Handles business logic for tasks.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Saves a new task to the database.
     */
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    /**
     * Returns all tasks from the database.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}