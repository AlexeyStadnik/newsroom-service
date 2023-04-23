package com.newsroom.newsroomservice.mapper;

import com.newsroom.newsroomservice.entity.SummaryEntity;
import com.newsroom.newsroomservice.model.AiResults;
import com.service.model.rest.RestNewSummary;
import com.service.model.rest.RestSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(
        imports = {
                UUID.class
        }
)
public interface SummaryMapper {

    SummaryMapper INSTANCE = Mappers.getMapper(SummaryMapper.class);


    @Mapping(target = "originalContent", source = "restNewSummary.article")
    @Mapping(target = "language", source = "restNewSummary.outputLanguage")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    SummaryEntity toEntity(RestNewSummary restNewSummary, AiResults aiContentResults);

    RestSummary toModel(SummaryEntity summaryEntity);

    List<RestSummary> toModels(List<SummaryEntity> summaryEntity);
}
