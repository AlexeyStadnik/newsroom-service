package com.newsroom.newsroomservice.client;

import com.service.model.rest.RestLanguage;

import java.util.List;

public interface DeeplClient {
    List<String> translate(List<String> contentToTranslate, RestLanguage source, RestLanguage target);
}
