package com.scheduler.controllers;

import com.scheduler.services.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final TaskService taskService;

    public UserController(TaskService taskService) {
        this.taskService = taskService;
    }

//    @Operation(summary = "Get tasks of user", description = "Retrieve list of tasks that belong to specific user")
//    @GetMapping("/{userId}/tasks")
//    public List<TaskDTO> getTasksByUser(@PathVariable Long userId) {
//        return taskService.getTasksByUser(userId);
//    }
}