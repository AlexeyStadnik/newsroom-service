package com.newsroom.newsroomservice;

import com.newsroom.newsroomservice.helper.TestDataHelper;
import com.newsroom.newsroomservice.repository.SummaryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {
        PostgresInitializer.class
})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest {

    protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected SummaryRepository summaryRepository;

    @Autowired
    protected TestDataHelper testDataHelper;

    @AfterEach
    public void clearDatabase() {
        summaryRepository.deleteAll();
    }

}
