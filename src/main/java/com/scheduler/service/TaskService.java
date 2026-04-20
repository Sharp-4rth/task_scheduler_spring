package com.scheduler.service;

import com.scheduler.dtos.CreateTaskRequest;
import com.scheduler.dtos.TaskDTO;
import com.scheduler.dtos.TaskMapper;
import com.scheduler.models.Task;
import com.scheduler.models.TaskStatus;
import com.scheduler.models.User;
import com.scheduler.repository.TaskRepository;
import com.scheduler.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, TaskMapper taskMapper, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.userRepository = userRepository;
    }

    public TaskDTO createTask(CreateTaskRequest dto) {

        // DTO → Entity
        Task task = taskMapper.toEntity(dto);

        // Get user
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        task.setStatus(TaskStatus.PENDING);

        // Set relationship
        user.addTask(task);

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


    public List<TaskDTO> getTasksByUser(Long userId) {
        return taskRepository.findByUserId(userId)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskDTO> getTaskByName(String name) {
        return taskRepository.findByName(name)
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