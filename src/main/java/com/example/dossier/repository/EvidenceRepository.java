package com.example.dossier.repository;

import com.example.dossier.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

    List<Evidence> findByInvestigationCase_Id(Long caseId);

}