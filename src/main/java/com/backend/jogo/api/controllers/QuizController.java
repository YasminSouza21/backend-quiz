package com.backend.jogo.api.controllers;

import com.backend.jogo.api.domain.quiz.Category;
import com.backend.jogo.api.domain.quiz.Response;
import com.backend.jogo.api.dto.RequestCategoryDTO;
import com.backend.jogo.api.dto.ResponseCategoryDTO;
import com.backend.jogo.api.dto.ResponseQuestionsAndResponsesDTO;
import com.backend.jogo.api.services.quiz.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("quiz")
@CrossOrigin(origins = "*")    
public class QuizController {

    @Autowired
    private QuizService quizService;

    @PostMapping("/category")
    @Transactional
    public ResponseEntity<ResponseCategoryDTO> creationCategory(@RequestBody RequestCategoryDTO categoryDto, UriComponentsBuilder uriBuilder){
        Category category = quizService.createCategoryAndQuestions(categoryDto);

        URI uri = uriBuilder.path("/quiz/{id}").buildAndExpand(category.getId()).toUri();

        return ResponseEntity.created(uri).body(new ResponseCategoryDTO(category.getName()));
    }

    @DeleteMapping("/category/{category}")
    @Transactional
    public ResponseEntity<ResponseCategoryDTO> deleteCategory(@PathVariable String category){
        quizService.deleteCategory(category);
        quizService.resetAutoIncrement();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<ResponseQuestionsAndResponsesDTO>> findQuestionsWithResponses(@PathVariable Long categoryId){
        List<ResponseQuestionsAndResponsesDTO> list = quizService.findQuestionsWithResponses(categoryId)
                .stream().map(e -> {
                        List<String> responses = new ArrayList<>();
                        String responseAnswer = null;

                        for (Response response : e.getResponses()){
                            responses.add(response.getText());
                            if(!response.getResponseCorrect().isEmpty()){
                                responseAnswer = response.getResponseCorrect();
                            }
                        }

                       return new ResponseQuestionsAndResponsesDTO(e.getId(), e.getText(), responses, responseAnswer);
                }).toList();

        return ResponseEntity.ok().body(list);
    }
}
