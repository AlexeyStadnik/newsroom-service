package com.newsroom.newsroomservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.Map;

@Controller
public class OpenapiController {
    public static final String OPENAPI_YAML_LOCATION = "classpath:/openapi/openapi.yaml";
    private final String openapiJson;
    private final String openapiYaml;


    @SneakyThrows
    public OpenapiController(final ResourceLoader resourceLoader,
                             final ObjectMapper objectMapper) {

        final Yaml yaml = yaml();
        final Map<?, ?> swaggerYaml = loadYaml(yaml, resourceLoader, OPENAPI_YAML_LOCATION);

        this.openapiJson = objectMapper.writeValueAsString(swaggerYaml);
        this.openapiYaml = yaml.dump(swaggerYaml);
    }

    @ResponseBody
    @GetMapping(value = "newsroom-service/v1/openapi.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String swaggerJson() {
        return openapiJson;
    }

    @ResponseBody
    @GetMapping("newsroom-service/v1/openapi.yaml")
    public String swaggerYaml() {
        return openapiYaml;
    }

    private Map<?, ?> loadYaml(
            final Yaml yaml,
            final ResourceLoader resourceLoader,
            final String location
    ) throws IOException {
        return yaml.load(resourceLoader.getResource(location).getInputStream());
    }

    private Yaml yaml() {
        final LoaderOptions options = new LoaderOptions();
        options.setAllowDuplicateKeys(false);
        return new Yaml(options);
    }
}
