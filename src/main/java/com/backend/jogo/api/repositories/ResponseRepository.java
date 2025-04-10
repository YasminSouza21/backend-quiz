package com.backend.jogo.api.repositories;

import com.backend.jogo.api.domain.quiz.Response;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
