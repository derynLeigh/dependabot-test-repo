package com.example.todo;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoItemControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private TodoItemRepository todoItemRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @BeforeEach
    public void setUp() {
        todoItemRepository.deleteAll();
    }
    
    @Test
    public void testCreateTodoItem() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Test Todo")))
                .andExpect(jsonPath("$.description", is("Test Description")))
                .andExpect(jsonPath("$.done", is(false)))
                .andExpect(jsonPath("$.createdAtFormatted", containsString("Created on")))
                .andExpect(jsonPath("$.createdAtFormatted", containsString("at")));
    }
    
    @Test
    public void testCreateTodoItemWithMissingTitle() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testCreateTodoItemWithMissingDescription() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testCreateTodoItemWithMissingDeadline() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testGetAllTodoItems() throws Exception {
        TodoItem todoItem1 = new TodoItem();
        todoItem1.setTitle("Todo 1");
        todoItem1.setDescription("Description 1");
        todoItem1.setDeadline(LocalDateTime.now().plusDays(1));
        todoItemRepository.save(todoItem1);
        
        TodoItem todoItem2 = new TodoItem();
        todoItem2.setTitle("Todo 2");
        todoItem2.setDescription("Description 2");
        todoItem2.setDeadline(LocalDateTime.now().plusDays(2));
        todoItemRepository.save(todoItem2);
        
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Todo 1")))
                .andExpect(jsonPath("$[1].title", is("Todo 2")));
    }
    
    @Test
    public void testGetTodoItemById() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        TodoItem saved = todoItemRepository.save(todoItem);
        
        mockMvc.perform(get("/api/todos/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Todo")))
                .andExpect(jsonPath("$.description", is("Test Description")));
    }
    
    @Test
    public void testUpdateTodoItem() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Original Title");
        todoItem.setDescription("Original Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        TodoItem saved = todoItemRepository.save(todoItem);
        
        TodoItem updatedTodoItem = new TodoItem();
        updatedTodoItem.setTitle("Updated Title");
        updatedTodoItem.setDescription("Updated Description");
        updatedTodoItem.setDeadline(LocalDateTime.now().plusDays(2));
        updatedTodoItem.setDone(true);
        
        mockMvc.perform(put("/api/todos/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodoItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.description", is("Updated Description")))
                .andExpect(jsonPath("$.done", is(true)));
    }
    
    @Test
    public void testMarkTodoItemAsDone() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        TodoItem saved = todoItemRepository.save(todoItem);
        
        TodoItem updatedTodoItem = new TodoItem();
        updatedTodoItem.setTitle("Test Todo");
        updatedTodoItem.setDescription("Test Description");
        updatedTodoItem.setDeadline(LocalDateTime.now().plusDays(1));
        updatedTodoItem.setDone(true);
        
        mockMvc.perform(put("/api/todos/" + saved.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodoItem)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.done", is(true)));
    }
    
    @Test
    public void testDeleteTodoItem() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        TodoItem saved = todoItemRepository.save(todoItem);
        
        mockMvc.perform(delete("/api/todos/" + saved.getId()))
                .andExpect(status().isNoContent());
        
        mockMvc.perform(get("/api/todos/" + saved.getId()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    public void testTimestampFormat() throws Exception {
        TodoItem todoItem = new TodoItem();
        todoItem.setTitle("Test Todo");
        todoItem.setDescription("Test Description");
        todoItem.setDeadline(LocalDateTime.now().plusDays(1));
        
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todoItem)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.createdAtFormatted", matchesPattern("Created on \\w+, \\d+ \\w+ at \\d{2}:\\d{2}")));
    }
}
