package com.newsroom.newsroomservice.client.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatGptResponse {

    private List<Choice> choices;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Choice {
        private Message message;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
