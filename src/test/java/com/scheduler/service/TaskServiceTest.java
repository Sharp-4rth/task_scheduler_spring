package com.scheduler.service;

import com.scheduler.model.Task;
import com.scheduler.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private TaskRepository mockRepository;
    private TaskService sut;

    @BeforeEach
    void setUp() {
        mockRepository = Mockito.mock(TaskRepository.class);
        sut = new TaskService(mockRepository);
    }

    @Test
    void shouldCreateTask() {
        Task task = new Task();
        task.setName("Test Task");

        Mockito.when(mockRepository.save(task)).thenReturn(task);

        Task savedTask = sut.createTask(task);

        assertNotNull(savedTask);
        assertEquals("Test Task", savedTask.getName());
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task();
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setName("Task 2");

        List<Task> mockTasks = List.of(task1, task2);

        Mockito.when(mockRepository.findAll()).thenReturn(mockTasks);

        List<Task> tasks = sut.getAllTasks();

        assertEquals(2, tasks.size());
    }

    @Test
    @DisplayName("Given task id does not exist, throw RuntimeException")
    void givenTaskIdDoesNotExist_throwsRuntimeException() {

        Long id = 999L;

        Mockito.when(mockRepository.findById(id))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            sut.getTaskById(id);
        });

        assertEquals("Task not found", exception.getMessage());
    }

    @Test
    void shouldScheduleTasksWithinTimeLimit() {
        Task task1 = new Task();
        task1.setName("High Priority");
        task1.setPriority(10);
        task1.setDuration(5);

        Task task2 = new Task();
        task2.setName("Medium Priority");
        task2.setPriority(5);
        task2.setDuration(4);

        Task task3 = new Task();
        task3.setName("Low Priority");
        task3.setPriority(1);
        task3.setDuration(2);

        ArrayList<Task> mockTasks = new ArrayList<>(List.of(task1, task2, task3));

        Mockito.when(mockRepository.findAll()).thenReturn(mockTasks);

        List<Task> result = sut.scheduleTasks();

        // maxTime = 8 → should pick task1 (5) + task3 (2) = 7
        assertEquals(2, result.size());
        assertTrue(result.contains(task1));
        assertTrue(result.contains(task3));
    }
}
