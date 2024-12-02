package com.todo.service;

import com.todo.entity.TodoItem;
import com.todo.exception.TodoItemNotFoundException;
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

    public TodoItem updateTodoItem(TodoItem todoItem) {
        validateItemIdExist(todoItem.getId());
        return todoRepository.save(todoItem);
    }

    private void validateItemIdExist(Integer todoItemId) {
        if (todoItemId == null || !todoRepository.existsById(todoItemId)) {
            throw new TodoItemNotFoundException();
        }
    }

    public TodoItem deleteTodoItem(Integer todoItemId) {
        validateItemIdExist(todoItemId);
        todoRepository.deleteById(todoItemId);
        return new TodoItem(todoItemId, null, null);
    }
}
