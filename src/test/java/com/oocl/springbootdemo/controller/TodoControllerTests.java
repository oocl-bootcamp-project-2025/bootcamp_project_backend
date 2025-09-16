package com.oocl.springbootdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.springbootdemo.object.Todo;
import com.oocl.springbootdemo.object.TodoRequest;
import com.oocl.springbootdemo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

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

    TodoRequest createTodoRequest(String text) throws Exception {
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setText(text);
        return todoRequest;
    }

    long createTodoReturnId(String text) throws Exception {
        ResultActions resultActions = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("""
                {
                    "text": "%s"
                }
                """, text)));
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        return new ObjectMapper().readTree(contentAsString).get("id").asLong();
    }

    @BeforeEach
    void reset() {
        todoRepository.clearAll();
    }

    //AC1
    @Test
    void should_get_empty_json_when_get_given_storage_contains_no_todos() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void should_get_1_todo_when_get_given_storage_contains_1_todo() throws Exception {
        TodoRequest todoRequest = createTodoRequest("Buy milk");
        todoRepository.save(todoRequest);

        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    //AC2
    @Test
    void should_create_todo_when_post_given_a_valid_body() throws Exception {
        TodoRequest todoRequest = createTodoRequest("Buy milk");

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("Buy milk"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_reject_when_post_given_todo_with_empty_text() throws Exception {
        TodoRequest todoRequest = createTodoRequest("");

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todoRequest)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_reject_when_post_given_todo_with_no_text() throws Exception {
        Todo todo = new Todo();

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_create_todo_and_ignore_id_when_post_given_a_valid_body_with_id() throws Exception {
        String requestBody = """
                            {
                                "id": 1,
                                "text": "Buy milk"
                            }
                        """;

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.text").value("Buy milk"))
                .andExpect(jsonPath("$.done").value(false));
    }

    //AC3
    @Test
    void should_update_todo_when_put_given_a_valid_body() throws Exception {
        long id = createTodoReturnId("Buy milk");

        String updatedRequestBody = """
                        {
                            "text": "Buy snacks",
                            "done": true
                        }
                """;

        mockMvc.perform(put("/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value("Buy snacks"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_update_todo_and_ignore_id_when_put_given_a_valid_body_with_id() throws Exception {
        long id = createTodoReturnId("Buy milk");

        String updatedRequestBody = String.format("""
                        {
                            "id": %d,
                            "text": "Buy milk-done",
                            "done": true
                        }
                """, id+1);

        mockMvc.perform(put("/todos/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value("Buy milk-done"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    void should_reject_when_put_given_non_exist_id() throws Exception {
        String updatedRequestBody = """
                        {
                            "text": "Buy milk-done",
                            "done": true
                        }
                """;

        mockMvc.perform(put("/todos/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_reject_when_put_given_incomplete_payload() throws Exception {
        String updatedRequestBody = """
                        {
                        }
                """;

        mockMvc.perform(put("/todos/-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isUnprocessableEntity());
    }

    //AC4
    @Test
    void should_response_No_Content_when_delete_given_a_valid_id() throws Exception {
        long id = createTodoReturnId("Buy milk");

        mockMvc.perform(delete("/todos/"+id))
                .andExpect(status().isNoContent());
    }

    @Test
    void should_response_Not_Found_when_delete_given_a_invalid_id() throws Exception {
        long id = createTodoReturnId("Buy milk");

        mockMvc.perform(delete("/todos/"+id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/todos/"+id))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_get_null_when_get_given_a_deleted_id() throws Exception {
        long id = createTodoReturnId("Buy milk");

        mockMvc.perform(delete("/todos/"+id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/todos/"+id))
                .andExpect(status().isNotFound());
    }

    //Other AC
    @Test
    void should_get_todo_when_get_given_a_valid_id() throws Exception {
        long id = createTodoReturnId("Buy milk");

        mockMvc.perform(get("/todos/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.text").value("Buy milk"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    void should_get_null_when_get_given_a_invalid_id() throws Exception {
        mockMvc.perform(get("/todos/1"))
                .andExpect(status().isNotFound());
    }
}
