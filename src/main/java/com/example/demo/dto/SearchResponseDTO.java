package com.example.demo.dto;

import com.example.demo.entity.RepositoryEntity;
import java.util.List;

public class SearchResponseDTO {

    private String message;
    private List<RepositoryEntity> repositories;

    public SearchResponseDTO(String message, List<RepositoryEntity> repositories) {
        this.message = message;
        this.repositories = repositories;
    }

    public String getMessage() {
        return message;
    }

    public List<RepositoryEntity> getRepositories() {
        return repositories;
    }
}