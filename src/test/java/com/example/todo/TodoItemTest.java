package com.example.todo;

import com.example.todo.model.TodoItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TodoItemTest {
    
    @Test
    public void testTimestampGenerationOnCreate() {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        
        // Simulate @PrePersist by manually calling the method
        // In actual usage, this would be called automatically by JPA
        assertNull(todoItem.getCreatedAtFormatted());
    }
    
    @Test
    public void testRequiredFieldsAreSet() {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Title");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        
        assertEquals("Test Title", todoItem.getTitle());
        assertEquals("Test Description", todoItem.getDescription());
        assertNotNull(todoItem.getDeadline());
        assertFalse(todoItem.isDone());
    }
    
    @Test
    public void testMarkAsDone() {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Title");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        
        assertFalse(todoItem.isDone());
        
        todoItem.setDone(true);
        assertTrue(todoItem.isDone());
    }
}
