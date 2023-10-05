package com.gothub.dto;

import com.gothub.model.Repository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@Tag("UnitTest")
@DisplayName("Repository Mapper Unit Tests")
public class RepositoryDtoTest {

    private final ModelMapper modelMapper = new ModelMapper();

    @Test
    @DisplayName("when convert a Repository entity to Repository dto, then correct")
    public void whenConvertRepositoryEntityToRepositoryDto_thenCorrect() {

        //given
        Repository book = Repository.builder()
                .id(1L)
                .name("Example")
                .languageUsed("Java")
                .accessed(5L)
                .createdAt(LocalDate.parse("2017-01-01"))
                .build();

        //when
        RepositoryDto repositoryDto = modelMapper.map(book, RepositoryDto.class);

        //then
        assertEquals(book.getId(), repositoryDto.getId());
        assertEquals(book.getName(), repositoryDto.getName());
        assertEquals(book.getCreatedAt(), repositoryDto.getCreatedAt());
        assertEquals(book.getAccessed(), repositoryDto.getAccessed());
    }

}
