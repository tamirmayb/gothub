package com.gothub.controller;

import com.gothub.GothubApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = GothubApplication.class)
@ActiveProfiles("test")
@DisplayName("Repository Controller REST API Tests")
@Tag("IntegrationTest")
public class RepositoryControllerRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("When GET Repositories list, then returns 200")
    public void whenGetRepositoriesList_thenReturns200() {

        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("/api/search/repositories", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    @DisplayName("Given non existent language, when GET repositories, then returns 200 and empty list")
    public void whenGetNonExistingLanguage_thenReturns200AndEmptyList() {

        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("/api/search/repositories?lang=aaa", List.class);

        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("Given limit = 3, when GET, then returns 200")
    public void givenLimit_whenGetRepositories_thenReturns200And3Items() {

        //when
        ResponseEntity<List> responseEntity = restTemplate.getForEntity("/api/search/repositories?limit=3", List.class);
        //then
        assertEquals(OK, responseEntity.getStatusCode());
        assertEquals(3, Objects.requireNonNull(responseEntity.getBody()).size());
    }

}
