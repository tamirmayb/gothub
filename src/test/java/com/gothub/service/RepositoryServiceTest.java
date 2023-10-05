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
@DisplayName("Repository Service Unit Tests")
public class RepositoryServiceTest {

    private ReposRepository reposRepositoryMock;
    private RepositoriesService repositoriesService;

    Repository rep1 = Repository.of(1L,
            "example", "",
            "Java",
            LocalDate.parse("2019-10-10").atStartOfDay(),
            10L);

    Repository rep2 = Repository.of(2L,
            "example2",
            "",
            "Java",
            LocalDate.parse("2017-10-10").atStartOfDay(),
            20L);

    Repository rep3 = Repository.of(3L,
            "example3",
            "",
            "Java",
            LocalDate.parse("2017-10-10").atStartOfDay(),
            30L);

    Repository rep4 = Repository.of(4L,
            "example4",
            "",
            "Go",
            LocalDate.parse("2018-10-10").atStartOfDay(),
            50L);

    Repository rep5 = Repository.of(5L,
            "example5",
            "",
            "TS",
            LocalDate.parse("2018-10-10").atStartOfDay(),
            20L);

    List<Repository> allRepos = Arrays.asList(rep1, rep2, rep3, rep4, rep5);


    @BeforeAll
    public void init() {
        reposRepositoryMock = mock(ReposRepository.class);
        repositoriesService = new RepositoriesService(reposRepositoryMock, new ModelMapper());
    }


    @Test
    @DisplayName("when list Repository, then Repositories are retrieved and ordered by rates")
    void whenGetRepositories_ThenRepositoriesRetrievedAndOrderedByRates() {

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
        assertEquals(5, repositories.size());
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

    @Test
    @DisplayName("when calculateRating called, calculate rating for each repository correctly ")
    void whenCalculateRating_CorrectRatingCalculated() {
        long maxAccess = 500;

        assertEquals(1, repositoriesService.calculateRating(100L, maxAccess));
        assertEquals(2, repositoriesService.calculateRating(200L, maxAccess));
        assertEquals(3, repositoriesService.calculateRating(300L, maxAccess));
        assertEquals(4, repositoriesService.calculateRating(400L, maxAccess));
        assertEquals(5, repositoriesService.calculateRating(500L, maxAccess));
    }

}
