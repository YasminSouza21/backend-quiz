package com.backend.jogo.api.repositories;

import com.backend.jogo.api.domain.quiz.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
