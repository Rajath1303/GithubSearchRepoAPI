package com.example.demo.service;

import com.example.demo.dto.SearchRequestDTO;
import com.example.demo.entity.RepositoryEntity;
import com.example.demo.repository.GithubRepository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GithubService {

    @Autowired
    private GithubRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public List<RepositoryEntity> searchAndSaveRepositories(SearchRequestDTO request) throws Exception {

        String url = "https://api.github.com/search/repositories?q="
                + request.getQuery()
                + "+language:" + request.getLanguage()
                + "&sort=" + request.getSort();

        String response = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);
        JsonNode items = root.get("items");

        List<RepositoryEntity> savedRepositories = new ArrayList<>();

        for (JsonNode node : items) {

            Long repoId = node.get("id").asLong();

            Optional<RepositoryEntity> existing = repository.findById(repoId);

            RepositoryEntity repo = existing.orElse(new RepositoryEntity());

            repo.setId(repoId);

            String description = node.get("description").asText("");

            if (description.length() > 255) {
                description = description.substring(0, 255);
            }

            repo.setName(node.get("name").asText());
            repo.setDescription(description);
            repo.setOwner(node.get("owner").get("login").asText());
            repo.setLanguage(node.get("language").isNull() ? null : node.get("language").asText());
            repo.setStars(node.get("stargazers_count").asInt());
            repo.setForks(node.get("forks_count").asInt());
            repo.setLastUpdated(Instant.parse(node.get("updated_at").asText()));

            RepositoryEntity savedRepo = repository.save(repo);
            savedRepositories.add(savedRepo);
        }

        return savedRepositories;
    }

    public List<RepositoryEntity> getRepositories(String language, Integer minStars, String sort) {

        List<RepositoryEntity> repos;

        if(language != null && minStars != null){
            repos = repository.findByLanguageAndStarsGreaterThanEqual(language, minStars);
        }
        else if(language != null){
            repos = repository.findByLanguage(language);
        }
        else if(minStars != null){
            repos = repository.findByStarsGreaterThanEqual(minStars);
        }
        else{
            repos = repository.findAll();
        }

        if(sort.equals("forks")){
            repos.sort(Comparator.comparing(RepositoryEntity::getForks).reversed());
        }
        else if(sort.equals("updated")){
            repos.sort(Comparator.comparing(RepositoryEntity::getLastUpdated).reversed());
        }
        else{
            repos.sort(Comparator.comparing(RepositoryEntity::getStars).reversed());
        }

        return repos;
    }
}