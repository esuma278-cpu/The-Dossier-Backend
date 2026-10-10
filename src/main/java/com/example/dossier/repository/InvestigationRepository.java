package com.example.dossier.repository;

import com.example.dossier.entity.Investigation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvestigationRepository
        extends JpaRepository<Investigation, Long> {

    List<Investigation> findByInvestigationCase_Id(Long caseId);

    List<Investigation> findByInvestigator_Id(Long investigatorId);
}