package com.example.hackathon.domain.feed.openai;

import static com.example.hackathon.global.error.exception.ErrorCode.GPT_INVALID_ERROR;

import com.example.hackathon.global.error.exception.CustomException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAiPrompt {

    @Value("${open-ai.key}")
    private String apiKey;

    @Value("${open-ai.infer-model}")
    private String gptModel;

    private static final String EXAMPLE_1 = "내 생일 파티에 너만 못 온 그날";
    private static final String EXAMPLE_2 = "혜진이가 엄청 혼났던 그날";
    private static final String EXAMPLE_3 = "지원이가 여친이랑 헤어진 그날";
    private static final String EXAMPLE_4 = "걔는 언제나 네가 없이 그날";
    private static final String EXAMPLE_5 = "너무 멋있는 옷을 입고 그날";
    private static final String HEAD = "영어로 되어있는 주제를 바탕으로, 뒤에 나오는 한글 예시 가사 포멧으로 만들어줘 주제: ";
    private static final String TAIL = "3줄의 신나는 분위기의 가사를 만들어주는데 무조건 \"그날\"로 한 줄의 가사가 끝나야해 절대 가운대에 \"그날\" 이 있으면 안돼. 내 말에 절대 대답하지 말고, 만든 결과값만 보여줘";
    public String formatQuestion(String photoAnalysisResult) {
        String format = EXAMPLE_1 + EXAMPLE_2 + EXAMPLE_3 + EXAMPLE_4 + EXAMPLE_5;
        String result = HEAD + photoAnalysisResult + format + TAIL;
        return result;
    }

    public String sendRequestToOpenAi(String questionRequest) {
        //String question
        HttpClient client = HttpClient.newHttpClient();
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", gptModel);
        JSONArray messages = new JSONArray();
        messages.put(new JSONObject().put("role", "user").put("content", questionRequest));
        requestBody.put("messages", messages);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString(), StandardCharsets.UTF_8))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return getContent(response.body());
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
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
