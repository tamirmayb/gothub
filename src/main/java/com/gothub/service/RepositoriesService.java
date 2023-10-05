package com.gothub.service;

import com.gothub.dto.RepositoryDto;
import com.gothub.model.Repository;
import com.gothub.repository.ReposRepository;
import com.gothub.utils.Utils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepositoriesService implements IRepositoriesService {
    private final static int DEFAULT_FETCH_LIMIT = 10;

    private final ReposRepository reposRepository;

    private final ModelMapper modelMapper;

    /**
     * Used for fetching repositories with optional parameters for filtering
     * In order to prevent unneeded long fetching if limit is null DEFAULT_FETCH_LIMIT items are fetched
     * @param limit
     * @param fromStr
     * @param language
     * @return
     */
    public List<RepositoryDto> getRepositories(Long limit, String fromStr, String language) {
        long topLimit = limit != null ? limit : DEFAULT_FETCH_LIMIT;

        long maxOfAccessed = reposRepository.maxOfAccessed();
        LocalDateTime from = Utils.toDateTime(fromStr);

        return reposRepository.findAll()
                .stream()
                .filter(filterRepositoriesPredicate(language, from))
                .map(repository-> toDto(repository, maxOfAccessed))
                .sorted(Comparator.comparing(RepositoryDto::getRating).reversed())
                .limit(topLimit)
                .collect(Collectors.toList());
    }

    /**
     * Converts Repository to RepositoryDto and calculates the rating (stars) for the Repository
     * @param repository
     * @param sum
     * @return
     */
    public RepositoryDto toDto(Repository repository, long sum) {
        RepositoryDto repositoryDto = modelMapper.map(repository, RepositoryDto.class);
        repositoryDto.setRating(calculateRating(repositoryDto.getAccessed(), sum));
        return repositoryDto;
    }

    public int calculateRating(Long repositoryAccessed, Long maxOfAccessed) {
        return Utils.calculateRating(repositoryAccessed, maxOfAccessed);
    }

    /**
     * Used for creation of a Predicate which is used for filtering repositories
     * Both params are optional so the filter should ignore empty values for any of them
     * @param language
     * @param from
     * @return
     */
    private static Predicate<Repository> filterRepositoriesPredicate(String language, LocalDateTime from) {
        return repository -> (
                (from == null ||
                        repository.getCreatedAt().isEqual(from) ||
                        repository.getCreatedAt().isAfter(from)) &&
                        (language == null || language.equalsIgnoreCase(repository.getLanguageUsed())));
    }
}
