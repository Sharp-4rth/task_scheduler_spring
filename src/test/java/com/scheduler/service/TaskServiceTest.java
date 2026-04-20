//package com.scheduler.service;
//
//import com.scheduler.dtos.CreateTaskRequest;
//import com.scheduler.dtos.TaskDTO;
//import com.scheduler.dtos.TaskMapper;
//import com.scheduler.models.Task;
//import com.scheduler.models.TaskStatus;
//import com.scheduler.models.User;
//import com.scheduler.repository.TaskRepository;
//import com.scheduler.repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//class TaskServiceTest {
//
//    private TaskRepository mockRepository;
//    private TaskMapper taskMapper;
//    private UserRepository userRepository;
//    private TaskService sut;
//
//    @BeforeEach
//    void setUp() {
//        mockRepository = Mockito.mock(TaskRepository.class);
//        taskMapper = Mockito.mock(TaskMapper.class);
//        userRepository = Mockito.mock(UserRepository.class);
//        sut = new TaskService(mockRepository, taskMapper, userRepository);
//    }
//
//    @Test
//    void shouldCreateTask() {
//
//        // Arrange
//        CreateTaskRequest request = new CreateTaskRequest();
//        request.setName("Test Task");
//        request.setUserId(1L);
//
//        Task task = new Task();
//        task.setName("Test Task");
//
//        User user = new User();
//        user.setId(1L);
//
//        Task savedTask = new Task();
//        savedTask.setName("Test Task");
//
//        TaskDTO responseDTO = new TaskDTO(
//                1L, "Test Task", 0, 0, null, TaskStatus.PENDING
//        );
//
//        // Mock behavior
//        when(taskMapper.toEntity(request)).thenReturn(task);
//        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
//        when(mockRepository.save(task)).thenReturn(savedTask);
//        when(taskMapper.toDTO(savedTask)).thenReturn(responseDTO);
//
//        // Act
//        TaskDTO result = sut.createTask(request);
//
//        // Assert
//        assertEquals("Test Task", result.getName());
//    }
////
////    @Test
////    void shouldReturnAllTasks() {
////        Task task1 = new Task();
////        task1.setName("Task 1");
////
////        Task task2 = new Task();
////        task2.setName("Task 2");
////
////        List<Task> mockTasks = List.of(task1, task2);
////
////        when(mockRepository.findAll()).thenReturn(mockTasks);
////
////        List<Task> tasks = sut.getAllTasks();
////
////        assertEquals(2, tasks.size());
////    }
////
////    @Test
////    @DisplayName("Given task id does not exist, throw NotFoundException")
////    void givenTaskIdDoesNotExist_throwsNotFoundException() {
////
////        Long id = 999L;
////
////        when(mockRepository.findById(id))
////                .thenReturn(Optional.empty());
////
////        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
////            sut.getTaskById(id);
////        });
////
////        assertEquals("Task not found", exception.getReason());
////    }
////
////    @Test
////    void shouldScheduleTasksWithinTimeLimit() {
////        Task task1 = new Task();
////        task1.setName("High Priority");
////        task1.setPriority(10);
////        task1.setDuration(5);
////
////        Task task2 = new Task();
////        task2.setName("Medium Priority");
////        task2.setPriority(5);
////        task2.setDuration(4);
////
////        Task task3 = new Task();
////        task3.setName("Low Priority");
////        task3.setPriority(1);
////        task3.setDuration(2);
////
////        ArrayList<Task> mockTasks = new ArrayList<>(List.of(task1, task2, task3));
////
////        when(mockRepository.findAll()).thenReturn(mockTasks);
////
////        List<Task> result = sut.scheduleTasks();
////
////        // maxTime = 8 → should pick task1 (5) + task3 (2) = 7
////        assertEquals(2, result.size());
////        assertTrue(result.contains(task1));
////        assertTrue(result.contains(task3));
////    }
//}
