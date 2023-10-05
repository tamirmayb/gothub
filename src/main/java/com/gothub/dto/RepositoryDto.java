package com.gothub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class RepositoryDto {

    private Long id;

    @NotBlank(message = "Repository name is mandatory")
    private String name;

    private String description;

    @NotNull(message = "Language used for this repository is mandatory")
    private String languageUsed;

    private Long accessed;

    @NotNull(message = "Repository creation date is mandatory")
    @DateTimeFormat
    private LocalDateTime createdAt;

    private int rating;

}
