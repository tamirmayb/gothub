package com.gothub.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "repositories")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode(of = { "id" })
public class Repository {

    @Id
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "language_used", nullable = false)
    private String languageUsed;

    @Column(name = "accessed")
    private Long accessed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static Repository of(Long id, String name, String description, String languageUsed, LocalDateTime createdAt, Long accessed) {
        return Repository.builder()
                .id(id)
                .name(name)
                .description(description)
                .languageUsed(languageUsed)
                .createdAt(createdAt)
                .accessed(accessed)
                .build();
    }
}
