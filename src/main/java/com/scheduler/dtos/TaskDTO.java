package com.scheduler.dtos;

import java.time.LocalDateTime;
import com.scheduler.models.TaskStatus;

public class TaskDTO {

    private Long id;
    private String name;
    private int duration;
    private int priority;
    private LocalDateTime deadline;
    private TaskStatus status;

    public TaskDTO(Long id, String name, int duration, int priority,
                   LocalDateTime deadline, TaskStatus status) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getPriority() {
        return priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }
}