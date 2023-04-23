package com.newsroom.newsroomservice.service;

import com.service.model.rest.RestNewSummary;
import com.service.model.rest.RestSummary;

import java.util.List;
import java.util.UUID;

public interface SummaryService {
    RestSummary generateArticleSummary(RestNewSummary restNewSummary);

    List<RestSummary> retrieveSummaries(Integer page, Integer size);

    RestSummary retrieveSummary(UUID summariesId);
}
