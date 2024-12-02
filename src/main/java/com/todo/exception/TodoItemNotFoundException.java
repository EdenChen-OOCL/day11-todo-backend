package com.todo.exception;

public class TodoItemNotFoundException extends RuntimeException {

    public TodoItemNotFoundException(String message) {
        super(message);
    }
}
