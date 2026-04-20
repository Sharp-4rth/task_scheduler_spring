package com.scheduler.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users") // Table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL
    )
    private List<Task> tasks = new ArrayList<>();

    public User() {}

    public Long getId() {
        return id;
    }

    public User(String username) {
        this.username = username;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

