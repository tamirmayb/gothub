package com.gothub.controller;

import com.gothub.dto.RepositoryDto;
import com.gothub.service.RepositoriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RequestMapping("/api/search")
@RestController
@Slf4j
public class RepositoryController {

    private final RepositoriesService repositoriesService;

    @Operation(summary = "Gets the list of repositories with optional filters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the repository",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid parameter supplied",
                    content = @Content)
            })
    @GetMapping(path = "/repositories", produces = {"application/json"})
    public ResponseEntity<List<RepositoryDto>> list(
            @RequestParam(required = false, name = "limit") Optional<Long> limit,
            @RequestParam(required = false, name = "from") String from,
            @RequestParam(required = false, name = "lang") String lang) {

        log.info("GET /repositories triggered");

        return ResponseEntity.ok(repositoriesService.getRepositories(limit.orElse(10L), from, lang));
    }

}
