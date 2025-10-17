package com.example.todo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "todo_items")
public class TodoItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Description is required")
    @Column(nullable = false, length = 1000)
    private String description;
    
    @NotNull(message = "Deadline is required")
    @Column(nullable = false)
    private LocalDateTime deadline;
    
    @Column(nullable = false)
    private boolean done = false;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private String createdAtFormatted;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        createdAtFormatted = formatCreatedAt(createdAt);
    }
    
    private String formatCreatedAt(LocalDateTime dateTime) {
        // Format: "Created on <day-of-week>, <day> <month> at <hour>:<minute>"
        // Example: "Created on Monday, 17 October at 14:30"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'Created on 'EEEE, d MMMM 'at' HH:mm");
        return dateTime.format(formatter);
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDateTime getDeadline() {
        return deadline;
    }
    
    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
    
    public boolean isDone() {
        return done;
    }
    
    public void setDone(boolean done) {
        this.done = done;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getCreatedAtFormatted() {
        return createdAtFormatted;
    }
    
    public void setCreatedAtFormatted(String createdAtFormatted) {
        this.createdAtFormatted = createdAtFormatted;
    }
}
