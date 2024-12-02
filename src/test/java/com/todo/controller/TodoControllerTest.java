package com.todo.controller;

import static org.assertj.core.api.Assertions.assertThat;

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

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class TodoControllerTest {

    @Autowired
    private MockMvc client;
    @Autowired
    private TodoService todoService;
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

}
