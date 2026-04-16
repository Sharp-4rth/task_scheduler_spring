package com.scheduler.service;

import com.scheduler.dtos.CreateTaskRequest;
import com.scheduler.dtos.TaskDTO;
import com.scheduler.dtos.TaskMapper;
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
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    public TaskDTO createTask(CreateTaskRequest dto) {

        // DTO → Entity
        Task task = taskMapper.toEntity(dto);

        // Save (save() returns an entity)
        Task saved = taskRepository.save(task);

        // Entity → DTO (what the client would see)
        return taskMapper.toDTO(saved);
    }


    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }


    public TaskDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        return taskMapper.toDTO(task);
    }

    public List<TaskDTO> scheduleTasks() {

        List<Task> tasks = taskRepository.findAll();

        // sort by priority (descending)
        tasks.sort((a, b) -> b.getPriority() - a.getPriority());

        int maxTime = 8;
        int currentTime = 0;

        List<Task> scheduledTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (currentTime + task.getDuration() <= maxTime) {
                scheduledTasks.add(task);
                currentTime += task.getDuration();
            }
        }

        return scheduledTasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }
}