package com.test.usersapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.jdbc.Sql;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerCreateTest {

    @Autowired
    private MockMvc mockMvc;

    private String url = "http://localhost:8080/users/create";


    @Test
    @SqlGroup({
            @Sql(value = "/script/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        }
    )
    public void createUser201() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getDataFile("request/create_user_201.json"))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void createUser400() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getDataFile("request/create_user_400_email.json"))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @SqlGroup({
            @Sql(value = "/script/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    }
    )
    public void createUser400Email() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getDataFile("request/create_user_201.json"))
                        .accept(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getDataFile("request/create_user_201.json"))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String getDataFile(String resource) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/".concat(resource))));
    }
}
