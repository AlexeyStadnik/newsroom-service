package com.newsroom.newsroomservice.config;

import com.newsroom.newsroomservice.client.DeeplClient;
import com.newsroom.newsroomservice.client.OpenAiClient;
import com.newsroom.newsroomservice.client.model.ChatGptResponse;
import com.newsroom.newsroomservice.helper.TestDataHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Profile("test")
@Configuration
@RequiredArgsConstructor
public class TestConfig {

    private final TestDataHelper testDataHelper;

    @Bean
    @Primary
    public DeeplClient deeplClient() {
        final DeeplClient mockClient = mock(DeeplClient.class);
        when(mockClient.translate(any(), any(), any())).thenReturn(testDataHelper.translationResults());
        return mockClient;
    }

    @Bean
    @Primary
    public OpenAiClient openAiClient() {
        final ChatGptResponse response = testDataHelper.getChatGptResponse();

        final OpenAiClient mockClient = mock(OpenAiClient.class);
        when(mockClient.processGpt(any())).thenReturn(response);
        return mockClient;
    }


}
