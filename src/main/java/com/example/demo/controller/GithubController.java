package com.example.demo.controller;

import com.example.demo.dto.SearchRequestDTO;
import com.example.demo.dto.SearchResponseDTO;
import com.example.demo.entity.RepositoryEntity;
import com.example.demo.service.GithubService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/github")
@RestController
@Tag(name = "GitHub", description = "Operations related to GitHub repositories")
public class GithubController {
    @Autowired
    private GithubService githubService;
    @PostMapping("/search")
    public SearchResponseDTO searchRepositories(@RequestBody SearchRequestDTO request) throws Exception {

        List<RepositoryEntity> repositories =
                githubService.searchAndSaveRepositories(request);

        return new SearchResponseDTO(
                "Repositories fetched and saved successfully",
                repositories
        );
    }

    @GetMapping("/repositories")
    public List<RepositoryEntity> getRepositories (
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer minStarts,
            @RequestParam(defaultValue = "stars") String sort) throws Exception{
        return githubService.getRepositories(language, minStarts, sort);
    }
}
