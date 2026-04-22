package com.scheduler.controllers;

import com.scheduler.dtos.TaskDTO;
import com.scheduler.services.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskViewController {
    private final TaskService taskService;

    public TaskViewController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping

    public String getTasks(Model model, Authentication authentication) {

        String username = authentication.getName();

        List<TaskDTO> tasks = taskService.getTasksForUserOrdered(username);

        model.addAttribute("tasks", tasks);

        model.addAttribute("username", username);

        return "tasks/index";

    }

    @PostMapping("/schedule")
    public String scheduleTasks() {
        taskService.scheduleTasks();
        return "redirect:/tasks";
    }

}

