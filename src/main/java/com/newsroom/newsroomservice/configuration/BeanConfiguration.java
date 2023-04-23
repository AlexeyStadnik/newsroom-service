package com.newsroom.newsroomservice.configuration;

import com.deepl.api.Translator;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@AllArgsConstructor
public class BeanConfiguration {

    private final AppConfiguration appConfiguration;

    @Bean
    public Translator deeplTranslator() {
        return new Translator(appConfiguration.getDeepl().getAuthKey());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
