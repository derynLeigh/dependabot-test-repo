package com.example.todo.controller;

import com.example.todo.model.TodoItem;
import com.example.todo.service.TodoItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoItemController {
    
    @Autowired
    private TodoItemService todoItemService;
    
    @PostMapping
    public ResponseEntity<TodoItem> createTodoItem(@Valid @RequestBody TodoItem todoItem) {
        TodoItem createdItem = todoItemService.createTodoItem(todoItem);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<TodoItem>> getAllTodoItems() {
        List<TodoItem> items = todoItemService.getAllTodoItems();
        return ResponseEntity.ok(items);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<TodoItem> getTodoItemById(@PathVariable Long id) {
        return todoItemService.getTodoItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<TodoItem> updateTodoItem(
            @PathVariable Long id,
            @Valid @RequestBody TodoItem todoItem) {
        try {
            TodoItem updatedItem = todoItemService.updateTodoItem(id, todoItem);
            return ResponseEntity.ok(updatedItem);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoItem(@PathVariable Long id) {
        todoItemService.deleteTodoItem(id);
        return ResponseEntity.noContent().build();
    }
}
