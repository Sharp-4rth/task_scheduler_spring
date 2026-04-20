package com.scheduler.config;

import com.scheduler.models.Task;
import com.scheduler.models.User;
import com.scheduler.repository.TaskRepository;
import com.scheduler.repository.UserRepository;
import com.scheduler.service.UserService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Scheduler API")
                        .version("1.0")
                        .description("API documentation for the Task Scheduler application"));
    }

    @Bean
    @Transactional
    public CommandLineRunner loadData(UserRepository userRepository, TaskRepository taskRepository, UserService userService) {
        return args -> {

            System.out.println("DataLoader running...");

            if (userRepository.count() == 0) {

                User user1 = userService.createUser("Alice", "password");
                User user2 = userService.createUser("Bob", "password");
                User user3 = userService.createUser("Harry", "password");

                userRepository.save(user1);
                userRepository.save(user2);
                userRepository.save(user3);

                Task t1 = new Task("Do dishes", 2, 3, LocalDateTime.now().plusDays(1));
                Task t2 = new Task("Walk the dog", 5, 5, LocalDateTime.now().plusDays(2));
                Task t3 = new Task("Clean", 5, 5, LocalDateTime.now().plusDays(3));

                user1.addTask(t1);
                user2.addTask(t2);
                user3.addTask(t3);

                taskRepository.save(t1);
                taskRepository.save(t2);

                System.out.println("Seed data added");

            } else {
                System.out.println("Seed skipped");
            }
        };
    }
}
