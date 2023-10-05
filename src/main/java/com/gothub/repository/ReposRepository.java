package com.gothub.repository;

import com.gothub.model.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReposRepository extends JpaRepository<Repository, Long> {

    @Query(value = "SELECT MAX(accessed) FROM repositories", nativeQuery = true)
    long maxOfAccessed();
}

