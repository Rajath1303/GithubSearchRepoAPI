package com.example.demo.service;

import com.example.demo.entity.RepositoryEntity;
import com.example.demo.repository.GithubRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GithubServiceTest {

    @Mock
    private GithubRepository repository;

    @InjectMocks
    private GithubService githubService;

    @Test
    void testGetRepositoriesReturnsAllWhenNoFilters() {

        RepositoryEntity repo1 = new RepositoryEntity();
        repo1.setStars(100);

        RepositoryEntity repo2 = new RepositoryEntity();
        repo2.setStars(200);

        when(repository.findAll()).thenReturn(Arrays.asList(repo1, repo2));

        List<RepositoryEntity> result = githubService.getRepositories(null, null, "stars");

        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFilterByLanguage() {

        RepositoryEntity repo = new RepositoryEntity();
        repo.setLanguage("Java");

        when(repository.findByLanguage("Java"))
                .thenReturn(new java.util.ArrayList<>(Arrays.asList(repo)));

        List<RepositoryEntity> result =
                githubService.getRepositories("Java", null, "stars");

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getLanguage());

        verify(repository).findByLanguage("Java");
    }

    @Test
    void testFilterByMinStars() {

        RepositoryEntity repo = new RepositoryEntity();
        repo.setStars(500);

        when(repository.findByStarsGreaterThanEqual(100))
                .thenReturn(new java.util.ArrayList<>(Arrays.asList(repo)));

        List<RepositoryEntity> result =
                githubService.getRepositories(null, 100, "stars");

        assertEquals(1, result.size());
        assertTrue(result.get(0).getStars() >= 100);
    }

    @Test
    void testSortingByForks() {

        RepositoryEntity repo1 = new RepositoryEntity();
        repo1.setForks(5);

        RepositoryEntity repo2 = new RepositoryEntity();
        repo2.setForks(20);

        when(repository.findAll()).thenReturn(Arrays.asList(repo1, repo2));

        List<RepositoryEntity> result =
                githubService.getRepositories(null, null, "forks");

        assertEquals(20, result.get(0).getForks());
    }
}