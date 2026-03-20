package com.scheduler.service;

import com.scheduler.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @Test
    void shouldCreateTask() {
        Task task = new Task();
        task.setName("Test Task");

        Task savedTask = taskService.createTask(task);

        assertNotNull(savedTask.getId());
        assertEquals("Test Task", savedTask.getName());
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task();
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setName("Task 2");

        taskService.createTask(task1);
        taskService.createTask(task2);

        List<Task> tasks = taskService.getAllTasks();

        assertTrue(tasks.size() >= 2);
    }
}
