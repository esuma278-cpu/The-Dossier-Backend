package com.example.dossier.repository;

import com.example.dossier.entity.Suspect;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SuspectRepository extends JpaRepository<Suspect, Long> {

    List<Suspect> findByInvestigationCase_Id(Long caseId);

}