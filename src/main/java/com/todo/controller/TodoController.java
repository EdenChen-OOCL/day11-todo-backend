package com.todo.controller;

import com.todo.entity.TodoItem;
import com.todo.service.TodoService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo/todoItem")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("")
    public ResponseEntity<List<TodoItem>> getTodos() {
        List<TodoItem> todoItems = todoService.getTodoItems();
        return ResponseEntity.ok(todoItems);
    }
}
