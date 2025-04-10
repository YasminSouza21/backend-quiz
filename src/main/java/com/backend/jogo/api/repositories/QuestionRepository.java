package com.backend.jogo.api.repositories;

import com.backend.jogo.api.domain.quiz.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{

    List<Question> findByCategoryId(Long id);
}
