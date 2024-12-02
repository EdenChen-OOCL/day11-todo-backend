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

}
