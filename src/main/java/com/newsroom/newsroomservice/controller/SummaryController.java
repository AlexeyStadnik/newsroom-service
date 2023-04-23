package com.newsroom.newsroomservice.controller;

import com.newsroom.newsroomservice.service.SummaryService;
import com.service.controllers.api.SummaryApi;
import com.service.model.rest.RestNewSummary;
import com.service.model.rest.RestSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SummaryController implements SummaryApi {

    private final SummaryService summaryService;

    @Override
    public ResponseEntity<RestSummary> generateArticleSummary(final RestNewSummary restNewSummary) {
        final RestSummary generated = summaryService.generateArticleSummary(restNewSummary);
        return new ResponseEntity<>(generated, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<RestSummary>> retrieveSummaries(final Integer page, final Integer size) {
        final List<RestSummary> summaries = summaryService.retrieveSummaries(page, size);
        return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RestSummary> retrieveSummary(final UUID summariesId) {
        final RestSummary summary = summaryService.retrieveSummary(summariesId);
        return new ResponseEntity<>(summary, HttpStatus.OK);
    }
}
