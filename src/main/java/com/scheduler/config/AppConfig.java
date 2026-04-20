package com.scheduler.config;

import com.scheduler.models.Task;
import com.scheduler.models.User;
import com.scheduler.repository.TaskRepository;
import com.scheduler.repository.UserRepository;
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
    public CommandLineRunner loadData(UserRepository userRepository, TaskRepository taskRepository) {
        return args -> {

            System.out.println("DataLoader running...");

            if (userRepository.count() == 0) {

                User user1 = new User("Alice");
                User user2 = new User("Bob");
                User user3 = new User("Harry");

                userRepository.save(user1);
                userRepository.save(user2);

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
