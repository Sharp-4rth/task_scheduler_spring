package com.scheduler.controller;

import com.scheduler.model.Task;
import com.scheduler.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// IOC container?
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/schedule")
    public List<Task> scheduleTasks() {       // No need for input uses existing tasks
        return taskService.scheduleTasks();
    }

}
