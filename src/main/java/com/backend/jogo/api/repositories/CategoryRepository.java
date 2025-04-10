package com.backend.jogo.api.repositories;

import com.backend.jogo.api.domain.quiz.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);
}
