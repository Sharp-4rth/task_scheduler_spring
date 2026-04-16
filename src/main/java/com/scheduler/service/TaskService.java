package com.scheduler.service;

import com.scheduler.model.Task;
import com.scheduler.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }


    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
    }

    public List<Task> scheduleTasks() {
        List<Task> tasks = taskRepository.findAll();

        // sort by priority (descending)
        tasks.sort((a, b) -> b.getPriority() - a.getPriority());

        // greedy selection
        int maxTime = 8; // Maybe revisit this and tweak it
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