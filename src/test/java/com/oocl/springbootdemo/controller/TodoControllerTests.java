package com.oocl.springbootdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springbootdemo.object.Company;
import com.oocl.springbootdemo.object.Employee;
import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TodoRepository todoRepository;

    Todo createTodo(String text) throws Exception {
        Todo todo = new Todo();
        todo.setText(text);
        return todo;
    }

    @Test
    void should_get_empty_json_when_get_storage_contains_no_todos() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_create_todo_when_post_given_a_valid_body() throws Exception {
        Todo todo = createTodo("todo1");

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("todo1"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_get_todo_when_get_given_a_valid_id() throws Exception {
        Todo todo = createTodo("todo1");
        todoRepository.save(todo);

        mockMvc.perform(get("/todos/" + todo.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todo.getId()))
                .andExpect(jsonPath("$.text").value("todo1"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_get_todos_when_get_given_null() throws Exception {
        for(int i=1; i<4; i++) {
            Todo todo = createTodo("todo"+i);
            todoRepository.save(todo);
        }

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void should_update_todo_when_put_given_a_valid_body() throws Exception {
        Todo todo = createTodo("todo1");
        todoRepository.save(todo);

        String updatedRequestBody = """
                        {
                            "text": "todo1-done",
                            "done": true
                        }
                """;

        mockMvc.perform(put("/todos/" + todo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(todo.getId()))
                .andExpect(jsonPath("$.text").value("todo1-done"))
                .andExpect(jsonPath("$.done").value(true));
    }
}
