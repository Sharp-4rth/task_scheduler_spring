package com.scheduler.repository;

import com.scheduler.models.Task;
import com.scheduler.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
