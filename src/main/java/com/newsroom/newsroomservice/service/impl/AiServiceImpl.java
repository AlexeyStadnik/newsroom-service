package com.newsroom.newsroomservice.service.impl;

import com.newsroom.newsroomservice.client.DeeplClient;
import com.newsroom.newsroomservice.client.OpenAiClient;
import com.newsroom.newsroomservice.client.mapper.OpenAiMapper;
import com.newsroom.newsroomservice.client.model.ChatGptRequest;
import com.newsroom.newsroomservice.client.model.ChatGptResponse;
import com.newsroom.newsroomservice.model.AiResults;
import com.newsroom.newsroomservice.service.AiService;
import com.service.model.rest.RestLanguage;
import com.service.model.rest.RestNewSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private static final OpenAiMapper MAPPER = OpenAiMapper.INSTANCE;
    private final OpenAiClient openAiClient;
    private final DeeplClient deeplClient;

    @Override
    public AiResults summarize(final RestNewSummary summary) {
        final ChatGptRequest request = MAPPER.toChatGptRequest(summary);
        final ChatGptResponse response = openAiClient.processGpt(request);

        AiResults summarizationResults = MAPPER.toRestAiGptContentResults(response);
        if (summary.getOutputLanguage() != null && summary.getOutputLanguage() != RestLanguage.EN) {
            final List<String> translationResults
                    = deeplClient.translate(MAPPER.toTranslationModel(summarizationResults),
                    RestLanguage.EN, summary.getOutputLanguage());

            summarizationResults = MAPPER.fromTranslationModel(translationResults);
        }

        return summarizationResults;
    }
}
