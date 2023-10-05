package com.gothub.service;

import com.gothub.dto.RepositoryDto;
import com.gothub.exception.CustomDateTimeParseException;
import com.gothub.model.Repository;
import com.gothub.repository.ReposRepository;
import com.gothub.utils.Utils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RepositoriesService {
    private final static int DEFAULT_FETCH_LIMIT = 10;
    private final static String DATE_FORMAT = "yyyy-MM-dd";

    private final ReposRepository reposRepository;
    private final ModelMapper modelMapper;

    public List<RepositoryDto> getRepositories(Long limit, String fromStr, String language) {
        long topLimit = limit != null ? limit : DEFAULT_FETCH_LIMIT;

        long maxOfAccessed = reposRepository.maxOfAccessed();
        LocalDate from = toDateTime(fromStr);

        return reposRepository.findAll()
                .stream()
                .filter(filterRepositoriesPredicate(language, from))
                .map(repository-> toDto(repository, maxOfAccessed))
                .sorted(Comparator.comparing(RepositoryDto::getRating).reversed())
                .limit(topLimit)
                .collect(Collectors.toList());
    }

    private static Predicate<Repository> filterRepositoriesPredicate(String language, LocalDate from) {
        return repository -> (from == null || repository.getCreatedAt()
                .plusDays(1)
                .isAfter(from)) &&
                (language == null || language.equalsIgnoreCase(repository.getLanguageUsed()));
    }

    private LocalDate toDateTime(String input) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            return input != null ? LocalDate.parse(input, formatter) : null;
        } catch (DateTimeParseException e) {
            throw new CustomDateTimeParseException("Invalid from date input value, should be yyyy-MM-dd");
        }
    }

    private RepositoryDto toDto(Repository repository, long sum) {
        RepositoryDto repositoryDto = modelMapper.map(repository, RepositoryDto.class);
        repositoryDto.setRating(calculateRating(repositoryDto.getAccessed(), sum));
        return repositoryDto;
    }

    public int calculateRating(Long repositoryAccessed, Long maxOfAccessed) {
        return Utils.calculateRating(repositoryAccessed, maxOfAccessed);
    }
}
