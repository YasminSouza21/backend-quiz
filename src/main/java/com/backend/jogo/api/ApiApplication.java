package com.backend.jogo.api;

import com.backend.jogo.api.domain.quiz.Category;
import com.backend.jogo.api.domain.quiz.QuizData;
import com.backend.jogo.api.dto.RequestCategoryDTO;
import com.backend.jogo.api.services.ia_gemini.GeminiService;
import com.backend.jogo.api.services.quiz.QuizService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ApiApplication.class, args);
	}
}
