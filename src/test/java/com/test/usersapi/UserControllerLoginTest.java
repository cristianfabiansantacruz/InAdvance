package com.test.usersapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerLoginTest {

    @Autowired
    private MockMvc mockMvc;

    private String url = "http://localhost:8080/users/login";
    private String urlCreate = "http://localhost:8080/users/create";


    @Test
    @SqlGroup({
            @Sql(value = "/script/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        }
    )
    public void loginUser200() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(urlCreate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getDataFile("request/create_login_user_200.json"))
                        .accept(MediaType.APPLICATION_JSON)
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getDataFile("request/login_user_200.json"))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SqlGroup({
            @Sql(value = "/script/delete_data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    }
    )
    public void loginUser400NoFound() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getDataFile("request/login_user_200.json"))
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String getDataFile(String resource) throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/test/resources/".concat(resource))));
    }
}
