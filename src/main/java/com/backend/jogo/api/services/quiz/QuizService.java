package com.backend.jogo.api.services.quiz;

import com.backend.jogo.api.domain.quiz.Category;
import com.backend.jogo.api.domain.quiz.Question;
import com.backend.jogo.api.domain.quiz.QuizData;
import com.backend.jogo.api.domain.quiz.Response;
import com.backend.jogo.api.dto.RequestCategoryDTO;
import com.backend.jogo.api.repositories.CategoryRepository;
import com.backend.jogo.api.repositories.QuestionRepository;
import com.backend.jogo.api.repositories.ResponseRepository;
import com.backend.jogo.api.services.ia_gemini.GeminiService;
import com.google.gson.Gson;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EntityManager entityManager;


    public QuizData[] getInfoQuestionsApi(String categoryName) {
        Gson gson = new Gson();
        String responseJson = GeminiService.getGeminiQuestions(categoryName);
        QuizData[] quizDataArray = gson.fromJson(responseJson, QuizData[].class);

        return quizDataArray;
    }

    public Category createCategoryAndQuestions(RequestCategoryDTO categoryDTO){

        Category category = new Category(categoryDTO);

        QuizData[] quizData = getInfoQuestionsApi(category.getName());

        List<Question> questions = new ArrayList<>();

        for (QuizData quizDatum : quizData) {
            Question question = new Question(quizDatum.getQuestion(), category);

            List<Response> responses = new ArrayList<>();

            for (Map.Entry<String, String> option : quizDatum.getOptions().entrySet()) {
                Response response = new Response();

                response.setText(option.getValue());
                response.setQuestion(question);
                response.setCategory(question.getCategory());
                if (option.getValue().equals(quizDatum.getResponseAnswer())) {
                    response.setResponseCorrect(quizDatum.getResponseAnswer());
                    response.setCorrect(true);
                } else {
                    response.setResponseCorrect("");
                    response.setCorrect(false);
                }

                responses.add(response);
            }

            question.setResponses(responses);

            questions.add(question);
        }

        category.setQuestions(questions);

        categoryRepository.save(category);

        return category;
    }

    public void deleteCategory(String nameCategory){
        List<Category> existingCategories = categoryRepository.findByName(nameCategory);

        existingCategories.forEach(categoryRepository::delete);
    }

    public List<Question>  findQuestionsWithResponses(Long categoryId){
        return questionRepository.findByCategoryId(categoryId);
    }

    @Transactional
    public void resetAutoIncrement() {
        entityManager.createNativeQuery("ALTER TABLE categories AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE questions AUTO_INCREMENT = 1").executeUpdate();
        entityManager.createNativeQuery("ALTER TABLE responses AUTO_INCREMENT = 1").executeUpdate();
    }
}
