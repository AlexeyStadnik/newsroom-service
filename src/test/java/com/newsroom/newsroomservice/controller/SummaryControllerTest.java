package com.newsroom.newsroomservice.controller;

import com.newsroom.newsroomservice.AbstractIntegrationTest;
import com.newsroom.newsroomservice.entity.SummaryEntity;
import com.newsroom.newsroomservice.repository.SummaryRepository;
import com.service.model.rest.RestLanguage;
import com.service.model.rest.RestNewSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.newsroom.newsroomservice.helper.TestDataHelper.FAKER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SummaryControllerTest extends AbstractIntegrationTest {

    @Autowired
    private SummaryRepository summaryRepository;

    @Test
    public void shouldCreateSummary() throws Exception {
        //GIVEN
        final RestNewSummary restNewSummary = new RestNewSummary(FAKER.lorem().sentence(), RestLanguage.PT);

        // WHEN
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/newsroom-service/api/v1/summaries")
                                .contentType(APPLICATION_JSON)
                                .content(OBJECT_MAPPER.writeValueAsBytes(restNewSummary)))
                // THEN
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())));

        final long allSummaries = summaryRepository.count();
        assertThat(allSummaries).isEqualTo(1);
    }

    @Test
    public void shouldGetAllSummaries() throws Exception {
        //GIVEN
        testDataHelper.insertSummary(null);
        testDataHelper.insertSummary(null);
        testDataHelper.insertSummary(null);

        // WHEN
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/newsroom-service/api/v1/summaries")
                                .contentType(APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    public void shouldGetSingleSummary() throws Exception {
        //GIVEN
        final SummaryEntity summary = testDataHelper.insertSummary(null);


        // WHEN
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/newsroom-service/api/v1/summaries/{summaryId}",
                                        summary.getId())
                                .contentType(APPLICATION_JSON))
                // THEN
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(summary.getId().toString())));
    }

}
