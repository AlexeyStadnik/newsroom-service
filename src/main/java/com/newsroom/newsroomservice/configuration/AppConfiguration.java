package com.newsroom.newsroomservice.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppConfiguration {

    private Deepl deepl = new Deepl();
    private OpenAi openAi = new OpenAi();

    @Getter
    @Setter
    public static class OpenAi {
        private String host;
        private String authKey;
    }

    @Getter
    @Setter
    public static class Deepl {
        private String authKey;
    }
}
