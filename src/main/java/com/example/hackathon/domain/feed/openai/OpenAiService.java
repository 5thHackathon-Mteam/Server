package com.example.hackathon.domain.feed.openai;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenAiService {

    private final OpenAiImagePrompt openAiImagePrompt;
    private final OpenAiPrompt openAiPrompt;

    public String createGptContent(String imageUrl){
        String firstResponse = openAiImagePrompt.requestImage(imageUrl);
        String question = openAiPrompt.formatQuestion(firstResponse);
        return openAiPrompt.sendRequestToOpenAi(question);
    }
}
