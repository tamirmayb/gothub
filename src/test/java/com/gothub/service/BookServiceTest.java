package com.gothub.service;

import com.gothub.dto.RepositoryDto;
import com.gothub.exception.CustomDateTimeParseException;
import com.gothub.model.Repository;
import com.gothub.repository.ReposRepository;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(PER_CLASS)
@ActiveProfiles("test")
@Tag("UnitTest")
@DisplayName("Book Service Unit Tests")
public class BookServiceTest {

    private ReposRepository reposRepositoryMock;
    private RepositoriesService repositoriesService;

    Repository rep1 = Repository.builder().id(1L).name("example").languageUsed("Java").createdAt(LocalDate.parse("2019-10-10")).accessed(10L).build();
    Repository rep2 = Repository.builder().id(2L).name("example2").languageUsed("Java").createdAt(LocalDate.parse("2017-10-10")).accessed(20L).build();
    Repository rep3 = Repository.builder().id(3L).name("example3").languageUsed("Java").createdAt(LocalDate.parse("2017-10-10")).accessed(30L).build();
    Repository rep4 = Repository.builder().id(4L).name("example4").languageUsed("GO").createdAt(LocalDate.parse("2018-10-10")).accessed(50L).build();
    Repository rep5 = Repository.builder().id(5L).name("example5").languageUsed("TS").createdAt(LocalDate.parse("2018-10-10")).accessed(20L).build();

    List<Repository> allRepos = Arrays.asList(rep1, rep2, rep3, rep4, rep5);


    @BeforeAll
    public void init() {
        reposRepositoryMock = mock(ReposRepository.class);
        repositoriesService = new RepositoriesService(reposRepositoryMock, new ModelMapper());
    }


    @Test
    @DisplayName("when list Repository, then Repositories are retrieved and ordered by rates")
    void whenGetRepositories_ThenBooksRetrievedAndOrderedByRates() {

        //given
        when(reposRepositoryMock.findAll()).thenReturn(allRepos);
        when(reposRepositoryMock.maxOfAccessed()).thenReturn(50L);

        //when
        List<RepositoryDto> repositories = repositoriesService.getRepositories(null, null, null);

        //then
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());

        // check results are ordered according to rates
        assertEquals(rep4.getId(), repositories.get(0).getId());
        assertEquals(3, repositories.size());
    }

    @Test
    @DisplayName("when list Repository by language, only matching repos will be returned")
    void whenGetRepositories_InvalidFromDate_ThenExceptionThrown() {

        //given
        when(reposRepositoryMock.findAll()).thenReturn(allRepos);
        when(reposRepositoryMock.maxOfAccessed()).thenReturn(50L);

        Exception exception = assertThrows(CustomDateTimeParseException.class, () ->
                repositoriesService.getRepositories(null, "aaa", null));
        assertEquals("Invalid from date input value, should be yyyy-MM-dd", exception.getMessage());
    }

    @Test
    @DisplayName("when list Repository, filter by language and limit ")
    void whenGetRepositories_filterByLanguageAndLimit_ThenOnlyMatchingReposListed() {

        //given
        when(reposRepositoryMock.findAll()).thenReturn(allRepos);
        when(reposRepositoryMock.maxOfAccessed()).thenReturn(50L);

        //when
        List<RepositoryDto> repositories = repositoriesService.getRepositories(2L, null, "Java");

        //then
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());

        // check results are ordered according to rates
        assertEquals(rep3.getId(), repositories.get(0).getId());
        assertEquals(3L, repositories.get(0).getRating());
        assertEquals(2, repositories.size());
    }

    @Test
    @DisplayName("when list Repository, filter by date ")
    void whenGetRepositories_filterByFromDate_ThenOnlyMatchingReposListed() {

        //given
        when(reposRepositoryMock.findAll()).thenReturn(allRepos);
        when(reposRepositoryMock.maxOfAccessed()).thenReturn(50L);

        //when
        List<RepositoryDto> repositories = repositoriesService.getRepositories(50L, "2018-10-10", null);

        //then
        assertNotNull(repositories);
        assertFalse(repositories.isEmpty());

        // check results are ordered according to rates
        assertEquals(rep4.getId(), repositories.get(0).getId());
        assertEquals(5L, repositories.get(0).getRating());
        assertEquals(3, repositories.size());
    }

}
