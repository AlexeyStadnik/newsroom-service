package com.newsroom.newsroomservice.client.impl;

import com.newsroom.newsroomservice.client.OpenAiClient;
import com.newsroom.newsroomservice.client.model.ChatGptRequest;
import com.newsroom.newsroomservice.client.model.ChatGptResponse;
import com.newsroom.newsroomservice.configuration.AppConfiguration;
import com.newsroom.newsroomservice.exception.ServiceException;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenAiClientImpl implements OpenAiClient {

    private static final String CHAT_COMPLETIONS_API = "/v1/chat/completions";
    private final RestTemplate restTemplate;
    private final AppConfiguration appConfiguration;

    @Override
    @RateLimiter(name = "openai-ratelimiter")
    public ChatGptResponse processGpt(final ChatGptRequest model) {
        final String url = appConfiguration.getOpenAi().getHost() + CHAT_COMPLETIONS_API;
        final HttpHeaders headers = prepareHeader();

        final HttpEntity<ChatGptRequest> entity = new HttpEntity<>(model, headers);
        try {
            final ResponseEntity<ChatGptResponse> response
                    = restTemplate.postForEntity(url, entity, ChatGptResponse.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error while sending message to openAI", e);
            throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending message to openAI", e);
        }
    }

    private HttpHeaders prepareHeader() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + appConfiguration.getOpenAi().getAuthKey());
        return headers;
    }
}
