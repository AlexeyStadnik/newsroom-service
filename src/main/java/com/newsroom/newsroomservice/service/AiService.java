package com.newsroom.newsroomservice.service;

import com.newsroom.newsroomservice.model.AiResults;
import com.service.model.rest.RestNewSummary;

public interface AiService {
    AiResults summarize(RestNewSummary restNewSummary);

}
