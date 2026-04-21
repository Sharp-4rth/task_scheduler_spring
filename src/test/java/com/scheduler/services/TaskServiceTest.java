package com.scheduler.services;

import com.scheduler.dtos.CreateTaskRequest;
import com.scheduler.dtos.TaskDTO;
import com.scheduler.dtos.TaskMapper;
import com.scheduler.models.Task;
import com.scheduler.models.TaskStatus;
import com.scheduler.models.User;
import com.scheduler.repositories.TaskRepository;
import com.scheduler.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {

    private TaskRepository mockRepository;
    private TaskMapper taskMapper;
    private UserRepository userRepository;
    private TaskService sut;

    @BeforeEach
    void setUpMocks() {
        mockRepository = Mockito.mock(TaskRepository.class);
        taskMapper = Mockito.mock(TaskMapper.class);
        userRepository = Mockito.mock(UserRepository.class);
        sut = new TaskService(mockRepository, taskMapper, userRepository);
    }

    @BeforeEach
    void setUpAuth() {
        Authentication auth = Mockito.mock(Authentication.class);
        when(auth.getName()).thenReturn("Consuela");
        when(auth.isAuthenticated()).thenReturn(true);

        SecurityContext context = Mockito.mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);

        SecurityContextHolder.setContext(context);
    }

    @Test
    void shouldCreateTask() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest();
        request.setName("Test Task");

        Task task = new Task();

        User user = new User();
        user.setUsername("Consuela");

        Task savedTask = new Task();
        savedTask.setName("Test Task");

        TaskDTO responseDTO = new TaskDTO(
                1L, "Test Task", 0, 0, null, TaskStatus.PENDING
        );

        when(userRepository.findByUsername("Consuela")).thenReturn(Optional.of(user));
        when(taskMapper.toEntity(request)).thenReturn(task);
        when(mockRepository.save(task)).thenReturn(savedTask);
        when(taskMapper.toDTO(savedTask)).thenReturn(responseDTO);

        // Act
        TaskDTO result = sut.createTask(request);

        // Assert
        assertEquals("Test Task", result.getName());
        assertEquals(user, task.getUser());

        verify(userRepository).findByUsername("Consuela");
        verify(mockRepository).save(task);
    }

    @Test
    void shouldReturnAllTasks() {
        // Arrange
        User user = new User();
        user.setUsername("Consuela");

        Task task1 = new Task();
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setName("Task 2");

        List<Task> tasks = List.of(task1, task2);

        TaskDTO dto1 = new TaskDTO(1L, "Task 1", 0, 0, null, TaskStatus.PENDING);
        TaskDTO dto2 = new TaskDTO(2L, "Task 2", 0, 0, null, TaskStatus.PENDING);

        // Mock user lookup
        when(userRepository.findByUsername("Consuela"))
                .thenReturn(Optional.of(user));

        // Mock repository call
        when(mockRepository.findByUser(user))
                .thenReturn(tasks);

        // Mock mapping
        when(taskMapper.toDTO(task1)).thenReturn(dto1);
        when(taskMapper.toDTO(task2)).thenReturn(dto2);

        // Act
        List<TaskDTO> result = sut.getAllTasks();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Task 1", result.get(0).getName());
        assertEquals("Task 2", result.get(1).getName());
    }

    @Test
    @DisplayName("Given task id does not exist, throw NotFoundException")
    void givenTaskIdDoesNotExist_throwsNotFoundException() {
        // Arrange
        Long taskId = 99L;
        User user = new User();
        user.setUsername("Consuela");
        when(userRepository.findByUsername("Consuela"))
                .thenReturn(Optional.of(user));

        // Mock repository returning empty
        when(mockRepository.findById(taskId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> {
            sut.getTaskById(taskId); // adjust method name if needed
        });
    }

//    @Test
//    void shouldScheduleTasksWithinTimeLimit() {
//
//    }
}
