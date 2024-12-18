package com.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.todo.entity.TodoItem;
import com.todo.repository.TodoRepository;
import com.todo.service.TodoService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TodoControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private JacksonTester<List<TodoItem>> todoItemListJacksonTester;
    @Autowired
    private JacksonTester<TodoItem> todoItemJacksonTester;
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void should_return_all_todo_items() throws Exception {
        // Given
        List<TodoItem> expectData = todoRepository.findAll();
        // When
        String responseJSON = client.perform(MockMvcRequestBuilders.get("/todo/todoItem"))
                .andReturn().getResponse().getContentAsString();
        // Then
        List<TodoItem> todoItems = todoItemListJacksonTester.parseObject(responseJSON);
        assertThat(expectData).usingRecursiveComparison().isEqualTo(todoItems);
    }

    @Test
    public void should_return_new_todo_item_when_add_todo_item() throws Exception {
        // Given
        TodoItem expectData = new TodoItem("test", false);
        // When
        String responseJSON = client.perform(MockMvcRequestBuilders.post("/todo/todoItem")
                .contentType("application/json")
                .content(todoItemJacksonTester.write(expectData).getJson()))
                .andReturn().getResponse().getContentAsString();
        // Then
        TodoItem todoItemResponse = todoItemJacksonTester.parseObject(responseJSON);
        assertThat(expectData).usingRecursiveComparison().ignoringFields("id").isEqualTo(todoItemResponse);
    }

    @Test
    public void should_return_updated_todo_item_when_update_todo_item() throws Exception {
        // Given
        TodoItem todoItem = new TodoItem("test", false);
        TodoItem savedTodoItem = todoRepository.save(todoItem);

        TodoItem updateItem = new TodoItem(savedTodoItem.getId(), "test", false);
        updateItem.setText("new Test");
        updateItem.setDone(true);
        // When
        String responseJSON = client.perform(MockMvcRequestBuilders.put("/todo/todoItem/" + updateItem.getId())
                .contentType("application/json")
                .content(todoItemJacksonTester.write(updateItem).getJson()))
                .andReturn().getResponse().getContentAsString();
        // Then
        TodoItem todoItemResponse = todoItemJacksonTester.parseObject(responseJSON);
        assertThat(updateItem).usingRecursiveComparison().isEqualTo(todoItemResponse);
    }

    @Test
    public void should_return_exception_when_update_todo_item_not_found() throws Exception {
        // Given
        Integer wrongId = 9999;
        TodoItem updateItem = new TodoItem(wrongId, "test", false);
        // When
        String responseJSON = client.perform(MockMvcRequestBuilders.put("/todo/todoItem/" + updateItem.getId())
                .contentType("application/json")
                .content(todoItemJacksonTester.write(updateItem).getJson()))
                .andReturn().getResponse().getContentAsString();
        // Then
        assertThat(responseJSON).contains("Todo item not found");
    }

    @Test
    @Transactional
    public void should_return_deleted_todo_item_when_delete_todo_item() throws Exception {
        // Given
        TodoItem todoItem = new TodoItem("test", false);
        TodoItem savedTodoItem = todoRepository.save(todoItem);
        List<TodoItem> expectAll = todoRepository.findAll();
        Integer deleteTodoItemId = savedTodoItem.getId();
        // When
        String responseJSON = client.perform(MockMvcRequestBuilders.delete("/todo/todoItem/" + deleteTodoItemId))
                .andReturn().getResponse().getContentAsString();
        List<TodoItem> newItems = todoRepository.findAll();
        // Then
        TodoItem todoItemResponse = todoItemJacksonTester.parseObject(responseJSON);
        assertThat(expectAll.size() - 1).isEqualTo(newItems.size());
        assertEquals(deleteTodoItemId, todoItemResponse.getId());

    }

    @Test
    @Transactional
    public void should_return_exception_when_delete_todo_item_not_found() throws Exception {
        // Given
        Integer wrongId = 9999;
        TodoItem updateItem = new TodoItem(wrongId, "test", false);
        // When
        String responseJSON = client.perform(MockMvcRequestBuilders.delete("/todo/todoItem/" + updateItem.getId()))
                .andReturn().getResponse().getContentAsString();
        // Then
        assertThat(responseJSON).contains("Todo item not found");
    }

}
