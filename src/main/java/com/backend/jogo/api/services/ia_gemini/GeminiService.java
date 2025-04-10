package com.backend.jogo.api.services.ia_gemini;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Getter
@Service
public class GeminiService {

    @Value("${api.key}")
    private String apiKey;

    private static String API_KEY;

    private static final String URL_API = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    @PostConstruct
    public void apiKeyStatic(){
        API_KEY = apiKey;
    }

    public static String getApiKey(){
        return API_KEY;
    }

    public static String getGeminiQuestions(String categoryName)  {
        OkHttpClient client = new OkHttpClient();

        String jsonBody = "{ \"contents\": [{ \"parts\": [{\"text\": \"" +
                "Faca 10 pergunta sobre "+ categoryName +"? e me de 4 alternativas, 1,2,3,4 em formato de map, separando 1,2,3,4 das alternativas, de a resposta certa, facilite para mim mandando as question, options,e response_answer(sempre neste formato nas chaves) em formato de json, porque vou pegar e vou colocar em um array, sem codigo, e na response_answer so de o texto da respsta correta\"}] }] }";

        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url(URL_API + getApiKey())
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        String responses = null;
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                System.out.println("Erro: " + response.code() + " - " + response.message() + response.body());
            }

            assert response.body() != null;
            responses = response.body().string();

            JsonObject jsonResponse = JsonParser.parseString(responses).getAsJsonObject();

            JsonArray candidates = jsonResponse.getAsJsonArray("candidates");
            JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
            JsonObject content = firstCandidate.getAsJsonObject("content");
            JsonArray parts = content.getAsJsonArray("parts");
            String text = parts.get(0).getAsJsonObject().get("text").getAsString().replace("```json", "").replace("```", "");

            return text;
        }catch (IOException e){
            return "Falhou ao procurar a resposta";
        }
    }
}

