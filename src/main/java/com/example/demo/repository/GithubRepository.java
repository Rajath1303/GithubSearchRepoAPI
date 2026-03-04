package com.example.demo.repository;

import com.example.demo.entity.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubRepository extends JpaRepository<RepositoryEntity, Long> {

    List<RepositoryEntity> findByLanguage(String language);

    List<RepositoryEntity> findByStarsGreaterThanEqual(Integer stars);

    List<RepositoryEntity> findByLanguageAndStarsGreaterThanEqual(String language, Integer stars);
}