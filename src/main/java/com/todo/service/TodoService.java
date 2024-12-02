package com.todo.service;

import com.todo.entity.TodoItem;
import com.todo.repository.TodoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoItem> getTodoItems() {
        return todoRepository.findAll();
    }

    public TodoItem addTodo(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }
}
