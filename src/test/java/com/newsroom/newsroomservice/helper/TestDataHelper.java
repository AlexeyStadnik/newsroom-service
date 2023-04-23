package com.newsroom.newsroomservice.helper;

import com.github.javafaker.Faker;
import com.newsroom.newsroomservice.client.model.ChatGptResponse;
import com.newsroom.newsroomservice.entity.SummaryEntity;
import com.newsroom.newsroomservice.repository.SummaryRepository;
import com.service.model.rest.RestLanguage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class TestDataHelper {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static final Faker FAKER = new Faker();


    private final SummaryRepository summaryRepository;

    @SneakyThrows
    @Transactional
    public SummaryEntity insertSummary(final Consumer<SummaryEntity> modifier) {
        final SummaryEntity summary = new SummaryEntity();
        summary.setTitle(FAKER.lorem().sentence());
        summary.setContent(FAKER.lorem().sentence());
        summary.setLanguage(RestLanguage.PT);
        summary.setOriginalContent(FAKER.lorem().sentence());

        if (modifier != null) {
            modifier.accept(summary);
        }

        return summaryRepository.save(summary);
    }

    public ChatGptResponse getChatGptResponse() {
        final ChatGptResponse.Choice choice = new ChatGptResponse.Choice();
        final ChatGptResponse.Message message = new ChatGptResponse.Message();
        message.setContent("""
                        {
                            "title": "%s",
                            "content": "%s",
                            "tags": ["%s", "%s"],
                            "importance": 1,
                            "isInteresting": true
                        }
                                                """.formatted(
                        FAKER.backToTheFuture().character(),
                        FAKER.backToTheFuture().quote(),
                        FAKER.lorem().word(),
                        FAKER.lorem().word()
                ).trim()
        );
        message.setRole(FAKER.backToTheFuture().character());
        choice.setMessage(message);

        final ChatGptResponse response = new ChatGptResponse();
        response.setChoices(List.of(choice));
        return response;
    }

    public List<String> translationResults() {
        return List.of(
                FAKER.backToTheFuture().character(),
                FAKER.backToTheFuture().quote(),
                FAKER.lorem().word()
        );
    }

}
