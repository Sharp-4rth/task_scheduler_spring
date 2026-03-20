package com.example.scheduler.controller;

import com.example.scheduler.model.Task;
import com.example.scheduler.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles HTTP requests related to tasks.
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * POST /tasks
     * Creates a new task.
     */
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    /**
     * GET /tasks
     * Returns all tasks.
     */
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @PostMapping("/schedule")
    public List<Task> scheduleTasks() {       // No need for input uses existing tasks
        return taskService.scheduleTasks();   // No Logic in controller! just calls service
    }
}
