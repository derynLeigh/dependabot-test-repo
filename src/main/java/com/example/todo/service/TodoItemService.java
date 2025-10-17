package com.example.todo.service;

import com.example.todo.model.TodoItem;
import com.example.todo.repository.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {
    
    @Autowired
    private TodoItemRepository todoItemRepository;
    
    public TodoItem createTodoItem(TodoItem todoItem) {
        return todoItemRepository.save(todoItem);
    }
    
    public Optional<TodoItem> getTodoItemById(Long id) {
        return todoItemRepository.findById(id);
    }
    
    public List<TodoItem> getAllTodoItems() {
        return todoItemRepository.findAll();
    }
    
    public TodoItem updateTodoItem(Long id, TodoItem updatedTodoItem) {
        return todoItemRepository.findById(id)
                .map(existingItem -> {
                    existingItem.setTitle(updatedTodoItem.getTitle());
                    existingItem.setDescription(updatedTodoItem.getDescription());
                    existingItem.setDeadline(updatedTodoItem.getDeadline());
                    existingItem.setDone(updatedTodoItem.isDone());
                    return todoItemRepository.save(existingItem);
                })
                .orElseThrow(() -> new RuntimeException("Todo item not found with id: " + id));
    }
    
    public void deleteTodoItem(Long id) {
        todoItemRepository.deleteById(id);
    }
}
