package com.backend.jogo.api.dto;

import com.backend.jogo.api.domain.quiz.Response;

import java.util.List;

public record ResponseQuestionsAndResponsesDTO(Long idQuestion,
                                               String text,
                                               List<String> response,
                                               String correctAnswer)  {
}
