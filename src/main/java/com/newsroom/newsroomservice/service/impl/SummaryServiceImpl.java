package com.newsroom.newsroomservice.service.impl;

import com.newsroom.newsroomservice.entity.SummaryEntity;
import com.newsroom.newsroomservice.exception.ServiceException;
import com.newsroom.newsroomservice.mapper.SummaryMapper;
import com.newsroom.newsroomservice.model.AiResults;
import com.newsroom.newsroomservice.repository.SummaryRepository;
import com.newsroom.newsroomservice.service.AiService;
import com.newsroom.newsroomservice.service.SummaryService;
import com.newsroom.newsroomservice.utils.PagingUtils;
import com.service.model.rest.RestNewSummary;
import com.service.model.rest.RestSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SummaryServiceImpl implements SummaryService {

    private final SummaryRepository summaryRepository;
    private final AiService openAiService;

    @Override
    @Transactional
    public RestSummary generateArticleSummary(final RestNewSummary restNewSummary) {
        final AiResults aiResults = openAiService.summarize(restNewSummary);
        final SummaryEntity summaryEntity = SummaryMapper.INSTANCE.toEntity(restNewSummary, aiResults);
        final SummaryEntity savedSummaryEntity = summaryRepository.save(summaryEntity);
        return SummaryMapper.INSTANCE.toModel(savedSummaryEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RestSummary> retrieveSummaries(final Integer page, final Integer size) {
        final Pageable pageable = PagingUtils.toPageable(page, size);
        final List<SummaryEntity> summaryEntities
                = summaryRepository.findAllByOrderByCreatedAtDesc(pageable).getContent();
        return SummaryMapper.INSTANCE.toModels(summaryEntities);
    }

    @Override
    @Transactional(readOnly = true)
    public RestSummary retrieveSummary(final UUID summariesId) {
        final SummaryEntity summary = summaryRepository.findById(summariesId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "Summary not found"));
        return SummaryMapper.INSTANCE.toModel(summary);
    }
}
