package com.newsroom.newsroomservice.client.mapper;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsroom.newsroomservice.client.model.ChatGptRequest;
import com.newsroom.newsroomservice.client.model.ChatGptResponse;
import com.newsroom.newsroomservice.model.AiResults;
import com.service.model.rest.RestNewSummary;
import lombok.SneakyThrows;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mapper(
        imports = {
                UUID.class
        }
)
public interface OpenAiMapper {

    Integer MAX_TOKENS = 2024;
    double DEFAULT_TEMPERATURE = 1.0;
    double DEFAULT_TOP_P = 1.0;
    double DEFAULT_FREQUENCY_PENALTY = 0.0;
    double DEFAULT_PRESENCE_PENALTY = 0.0;
    OpenAiMapper INSTANCE = Mappers.getMapper(OpenAiMapper.class);
    ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);


    String NEWS_PROMPT = """         
            I want you to act as a journalist. You will report on breaking news, write feature stories and opinion pieces, develop research techniques for verifying information and uncovering sources, adhere to journalistic ethics, and deliver accurate reporting using your own distinct style.
                       Create a summary of the below article (started with 'the article'). The summary should be maximum 150-200 characters long.
                       Return the response in JSON format with keys: title, content, tags, isPortugalNews, isInteresting.
                       Generate 3 single words tags describing this news message and add them to JSON with the key name "tags" as a string array.
                       If the article is related to Portugal, add the key "isPortugalNews" with a boolean value true otherwise false.
                       If the article is related to one of the topics: Business, Economy, Investments, Real Estate, Immigration add the key "isInteresting" with a boolean value true otherwise false.
                       Rate the article importance depending on how important the news is from 1 to 5 and add the key "importance" with the int value.
                       The response should contain only JSON objects, without any additional text, and without the 'Result:' prefix.
                       Response format:
                                   {
                                       "title": ,
                                       "content": ,
                                       "tags": [],
                                       "importance": ,
                                       "isInteresting":
                                       "isPortugalNews":
                                   }
                       
                       the article:
                       %s
            """;


    default ChatGptRequest toChatGptRequest(final RestNewSummary summary) {
        return ChatGptRequest.builder()
                .model("gpt-3.5-turbo")
                .maxTokens(MAX_TOKENS)
                .messages(List.of(toMessage(summary.getArticle())))
                .temperature(DEFAULT_TEMPERATURE)
                .topP(DEFAULT_TOP_P)
                .frequencyPenalty(DEFAULT_FREQUENCY_PENALTY)
                .presencePenalty(DEFAULT_PRESENCE_PENALTY)
                .build();
    }

    default ChatGptRequest.Message toMessage(final String content) {
        return ChatGptRequest.Message.builder()
                .role("user")
                .content(toPrompt(content))
                .build();
    }

    default String toPrompt(final String content) {
        return String.format(NEWS_PROMPT, content);
    }

    @SneakyThrows
    default AiResults toRestAiGptContentResults(final ChatGptResponse response) {
        final List<ChatGptResponse.Choice> choices = response.getChoices();
        if (choices == null || choices.isEmpty()) {
            return null;
        }
        String text = choices.get(0).getMessage().getContent();
        // ChatGpt has an issue and sometimes returns some text before the JSON object, so we need to clean it up
        // If text does not start with { then remove everything before the first {
        if (!text.startsWith("{")) {
            text = text.substring(text.indexOf('{'));
        }
        return OBJECT_MAPPER.readValue(text, AiResults.class);
    }

    default List<String> toTranslationModel(final AiResults summarizationResults) {
        final List<String> forTranslation = new ArrayList<>();
        forTranslation.add(summarizationResults.getTitle());
        forTranslation.add(summarizationResults.getContent());
        forTranslation.addAll(summarizationResults.getTags());
        return forTranslation;
    }

    default AiResults fromTranslationModel(final List<String> translationResults) {
        final AiResults summarizationResults = new AiResults();
        summarizationResults.setTitle(translationResults.get(0));
        summarizationResults.setContent(translationResults.get(1));

        if (translationResults.size() > 2) {
            summarizationResults.setTags(translationResults.subList(2, translationResults.size()));
        }
        return summarizationResults;
    }
}
