package com.example.hackathon.domain.feed.openai;

import static com.example.hackathon.global.error.exception.ErrorCode.GPT_INVALID_ERROR;

import com.example.hackathon.global.error.exception.CustomException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OpenAiImagePrompt {

    @Value("${open-ai.key}")
    private String apiKey;

    @Value("${open-ai.image-model}")
    private String gptModel;

    @Value("${open-ai.requestUrl}")
    private String requestUrl;

    public String requestImage(String imageUrl) {
        HttpClient client = HttpClient.newHttpClient();
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", gptModel);
        requestBody.put("messages", new JSONObject[]{
                new JSONObject()
                        .put("role", "user")
                        .put("content", new JSONObject[]{
                        new JSONObject().put("type", "text").put("text", "What’s in this image?"),
                        new JSONObject().put("type", "image_url").put("image_url", new JSONObject().put("url", imageUrl))
                })
        });

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString(), StandardCharsets.UTF_8))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return getContent(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException(GPT_INVALID_ERROR);
        }
    }

    private String getContent(String response) {
        JSONObject obj = new JSONObject(response);
        JSONArray choices = obj.getJSONArray("choices");
        if (choices != null && choices.length() > 0) {
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            return message.getString("content");
        }
        throw new CustomException(GPT_INVALID_ERROR);
    }

}