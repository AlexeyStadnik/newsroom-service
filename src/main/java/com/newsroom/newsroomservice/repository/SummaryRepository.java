package com.newsroom.newsroomservice.repository;

import com.newsroom.newsroomservice.entity.SummaryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SummaryRepository extends JpaRepository<SummaryEntity, UUID> {

    Page<SummaryEntity> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
