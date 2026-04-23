package com.scheduler.services;

import com.scheduler.dtos.CreateTaskRequest;
import com.scheduler.dtos.TaskDTO;
import com.scheduler.dtos.TaskMapper;
import com.scheduler.models.Task;
import com.scheduler.models.TaskStatus;
import com.scheduler.models.User;
import com.scheduler.repositories.TaskRepository;
import com.scheduler.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
        String username = auth.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public TaskDTO createTask(CreateTaskRequest dto) {
        // DTO → Entity
        Task task = taskMapper.toEntity(dto);

        User user = getCurrentUser();

        task.setUser(user);

        // Save (save() returns an entity)
        Task saved = taskRepository.save(task);

        // Entity → DTO (what the client would see)
        return taskMapper.toDTO(saved);
    }

    public List<TaskDTO> getAllTasks() {
        User user = getCurrentUser();

        return taskRepository.findByUser(user)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public List<TaskDTO> getTaskByName(String name) {
        User user = getCurrentUser();

        List<Task> tasks = taskRepository.findByNameAndUser(name, user);

        return tasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    public TaskDTO getTaskById(Long id) {

        User user = getCurrentUser();

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return taskMapper.toDTO(task);

    }

    public void updateMaxTime(String username, int maxTime) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        user.setMaxTime(maxTime);

        userRepository.save(user);
    }

    public List<TaskDTO> getTasksForUserOrdered(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow();

        return taskRepository.findByUserOrderByScheduleOrderAsc(user)
                .stream()
                .map(taskMapper::toDTO)
                .toList();
    }

    // Assigns order and persists
    public List<TaskDTO> scheduleTasks() {
        User user = getCurrentUser();
        List<Task> tasks = new ArrayList<>(user.getTasks());

        // sort by priority DESC
        tasks.sort((a, b) -> b.getPriority() - a.getPriority());

        int maxTime = user.getMaxTime() != null ? user.getMaxTime() : 120;
        int currentTime = 0;
        int order = 0;
        for (Task task : tasks) {
            // reset everything first (important)
            task.setStatus(TaskStatus.UNSCHEDULED);
            task.setScheduleOrder(null);
            if (currentTime + task.getDuration() <= maxTime) {
                task.setStatus(TaskStatus.SCHEDULED);
                task.setScheduleOrder(order++);
                currentTime += task.getDuration();
            }
        }
        taskRepository.saveAll(tasks);
        return tasks.stream()
                .map(taskMapper::toDTO)
                .toList();
    }
}