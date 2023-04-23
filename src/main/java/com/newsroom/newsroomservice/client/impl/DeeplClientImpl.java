package com.newsroom.newsroomservice.client.impl;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import com.newsroom.newsroomservice.client.DeeplClient;
import com.newsroom.newsroomservice.exception.ServiceException;
import com.service.model.rest.RestLanguage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DeeplClientImpl implements DeeplClient {

    private final Translator deeplTranslator;

    @Override
    @SneakyThrows
    public List<String> translate(final List<String> toTranslate,
                                  final RestLanguage source,
                                  final RestLanguage target) {

        if (CollectionUtils.isEmpty(toTranslate)) {
            return new ArrayList<>();
        }

        final List<TextResult> textResults = deeplTranslator.translateText(
                toTranslate,
                "EN",
                toTargetLanguage(target)
        );

        return textResults.stream().map(TextResult::getText).collect(Collectors.toList());
    }


    private static String toTargetLanguage(final RestLanguage sourceLanguage) {
        switch (sourceLanguage) {
            case EN:
                return "EN-US";
            case PT:
                return "PT-PT";
            default:
                throw new ServiceException(HttpStatus.BAD_REQUEST,
                        String.format("Unsupported language: %s", sourceLanguage));
        }
    }
}
