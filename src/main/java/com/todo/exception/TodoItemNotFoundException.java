package com.todo.exception;

public class TodoItemNotFoundException extends RuntimeException {
    private static final String TODO_ITEM_NOT_FOUND = "Todo item not found";

    public TodoItemNotFoundException() {
        super(TODO_ITEM_NOT_FOUND);
    }
}
