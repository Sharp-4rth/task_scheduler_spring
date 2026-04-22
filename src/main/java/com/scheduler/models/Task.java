package com.scheduler.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "tasks") // Table name
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int duration;
    private int priority;
    private LocalDateTime deadline;
    @Column(name = "schedule_order")
    private Integer scheduleOrder;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    @ManyToOne       // <--- foreign key
    @JoinColumn(name = "user_id")
    private User user;

    // Empty, Spring/ JPA creates objects behind the scenes
    public Task() {}

    public Task(String name, int duration, int priority, LocalDateTime deadline) {
        this.name = name;
        this.duration = duration;
        this.priority = priority;
        this.deadline = deadline;
        this.status = TaskStatus.PENDING;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public void setScheduleOrder(Integer scheduleOrder) {
        this.scheduleOrder = scheduleOrder;
    }
}
