package com.todo.controller;

import com.todo.entity.TodoItem;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todo/todoItem")
public class TodoController {

    @GetMapping("")
    public ResponseEntity<List<TodoItem>> getTodos() {
        TodoItem todoItem = new TodoItem(1, "Buy milk", false);
        return ResponseEntity.ok(List.of(todoItem));
    }
}
