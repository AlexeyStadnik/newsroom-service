package com.newsroom.newsroomservice.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class ChatGptRequest {

    private String model;
    private Double temperature;
    @JsonProperty("max_tokens")
    private Integer maxTokens;
    @JsonProperty("top_p")
    private Double topP;
    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;
    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    private List<Message> messages;

    @Builder
    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;
    }

}
