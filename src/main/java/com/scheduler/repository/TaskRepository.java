package com.scheduler.repository;

import com.scheduler.models.Task;
import com.scheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByName(String name);
    List<Task> findByUser(User user);
    Optional<Task> findByIdAndUser(Long id, User user);
    List<Task> findByNameAndUser(String name, User user);
}