package com.backend.jogo.api.domain.quiz;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.Map;

@Getter
public class QuizData {
    private String question;
    private Map<String, String> options;
    @SerializedName("response_answer")
    private String responseAnswer;


}
