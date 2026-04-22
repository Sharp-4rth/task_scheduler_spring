package com.scheduler.controllers;

import com.scheduler.dtos.CreateTaskRequest;
import com.scheduler.dtos.TaskDTO;
import com.scheduler.models.User;
import com.scheduler.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(summary = "Create a task", description = "Create a new task in the database")
    @PostMapping
    public TaskDTO createTask(@RequestBody CreateTaskRequest request) {
        return taskService.createTask(request);
    }

    @Operation(summary = "Get all tasks", description = "Retrieve a list of all tasks")
    @GetMapping
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    // get task by name
    // I guess, call appropriate service method, which looks in the db
    @Operation(summary= "Get task by name", description = "Retrieve a task by name")
    @GetMapping("/{name}")
    public List<TaskDTO> getTaskByName(@PathVariable String name) {
        return taskService.getTaskByName(name);
    }

    @Operation(summary = "Get specific task", description = "Retrieve a task by id")
    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Schedule tasks", description = "Run the scheduling algorithm on tasks in the db")
    @GetMapping("/schedule")
    public List<TaskDTO> scheduleTasks() {       // No need for input uses existing tasks
        return taskService.scheduleTasks();
    }

}
