package com.example.dossier.repository;

import com.example.dossier.entity.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {

    List<Timeline> findByInvestigationCaseId(Long caseId);

}