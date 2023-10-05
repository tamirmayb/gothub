package com.gothub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gothub.service.RepositoriesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Tag("IntegrationTest")
@DisplayName("Repository Controller Integration Tests")
public class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RepositoriesService repositoriesService;

    public RepositoryControllerTest() {
    }

    @Test
    @DisplayName("get Repository, should return list with single item and 200")
    public void getRepositoryShouldReturn200() throws Exception {

        //given
        given(this.repositoriesService.getRepositories(100L, "Java", "2007-01-11")).willReturn(new ArrayList<>());

        //when-then
        this.mockMvc.perform(get("/api/search/repositories?limit=100&from=2007-01-11&lang=Java")
                .accept(APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
