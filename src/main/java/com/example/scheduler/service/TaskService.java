package com.example.scheduler.service;

import com.example.scheduler.model.Task;
import com.example.scheduler.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Task> scheduleTasks() {
        List<Task> tasks = taskRepository.findAll();

        // Step 1: sort by priority (descending)
        tasks.sort((a, b) -> b.getPriority() - a.getPriority());

        // Step 2: greedy selection
        int maxTime = 8; // Maybe revist this and tweak it
        int currentTime = 0;

        List<Task> scheduledTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (currentTime + task.getDuration() <= maxTime) {
                scheduledTasks.add(task);
                currentTime += task.getDuration();
            }
        }

        return scheduledTasks;
    }
}