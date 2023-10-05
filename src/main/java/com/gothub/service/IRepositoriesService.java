package com.gothub.service;

import com.gothub.dto.RepositoryDto;
import com.gothub.model.Repository;

import java.util.List;

public interface IRepositoriesService {
    List<RepositoryDto> getRepositories(Long limit, String fromStr, String language);

    RepositoryDto toDto(Repository repository, long sum);

    int calculateRating(Long repositoryAccessed, Long maxOfAccessed);
}
