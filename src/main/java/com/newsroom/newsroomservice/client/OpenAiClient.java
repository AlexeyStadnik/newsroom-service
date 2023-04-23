package com.newsroom.newsroomservice.client;

import com.newsroom.newsroomservice.client.model.ChatGptRequest;
import com.newsroom.newsroomservice.client.model.ChatGptResponse;

public interface OpenAiClient {
    ChatGptResponse processGpt(ChatGptRequest model);
}
