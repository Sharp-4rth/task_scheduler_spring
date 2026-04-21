package com.scheduler.config;

import com.scheduler.models.Task;
import com.scheduler.models.User;
import com.scheduler.repositories.TaskRepository;
import com.scheduler.repositories.UserRepository;
import com.scheduler.services.UserService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Scheduler API")
                        .version("1.0")
                        .description("API documentation for the Task Scheduler application")
                )
                .components(new Components()
                        .addSecuritySchemes("basicAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("basic")
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("basicAuth"));
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

                Task t1 = new Task("Do dishes", 3, 3, LocalDateTime.now().plusDays(1));
                Task t2 = new Task("Walk the dog", 5, 5, LocalDateTime.now().plusDays(2));
                Task t3 = new Task("Clean", 10, 5, LocalDateTime.now().plusDays(3));
                Task t4 = new Task("Study algorithms", 40, 5, LocalDateTime.now().plusDays(1));
                Task t5 = new Task("Gym session", 20, 4, LocalDateTime.now().plusDays(1));
                Task t6 = new Task("Buy groceries", 10, 3, LocalDateTime.now().plusDays(2));
                Task t7 = new Task("Prepare interview", 30, 5, LocalDateTime.now().plusDays(2));
                Task t8 = new Task("Call landlord", 10, 2, LocalDateTime.now().plusDays(1));
                Task t9 = new Task("Work on project", 40, 5, LocalDateTime.now().plusDays(3));
                Task t10 = new Task("Clean kitchen", 20, 3, LocalDateTime.now().plusDays(2));
                Task t11 = new Task("Watch lecture", 50, 4, LocalDateTime.now().plusDays(1));
                Task t12 = new Task("Fix bug in code", 30, 5, LocalDateTime.now().plusDays(1));
                Task t13 = new Task("Read documentation", 50, 3, LocalDateTime.now().plusDays(3));

                user1.addTask(t1); user1.addTask(t4); user1.addTask(t5); user1.addTask(t10); user1.addTask(t13);
                user2.addTask(t2); user2.addTask(t6); user2.addTask(t7); user2.addTask(t11);
                user3.addTask(t3); user3.addTask(t8); user3.addTask(t9); user3.addTask(t12);

                taskRepository.saveAll(List.of(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13));

                System.out.println("Seed data added");

            } else {
                System.out.println("Seed skipped");
            }
        };
    }
}
