package com.example.dossier.service;

import com.example.dossier.entity.Investigation;
import com.example.dossier.repository.InvestigationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestigationService {

    private final InvestigationRepository investigationRepository;

    public InvestigationService(InvestigationRepository investigationRepository) {
        this.investigationRepository = investigationRepository;
    }

    public Investigation submitInvestigation(Investigation investigation) {
        return investigationRepository.save(investigation);
    }

    public List<Investigation> getAllInvestigations() {
        return investigationRepository.findAll();
    }

    public Investigation getInvestigationById(Long id) {
        return investigationRepository.findById(id).orElse(null);
    }

    public List<Investigation> getInvestigationsByCase(Long caseId) {
        return investigationRepository.findByInvestigationCase_Id(caseId);
    }

    public List<Investigation> getInvestigationsByInvestigator(Long investigatorId) {
        return investigationRepository.findByInvestigator_Id(investigatorId);
    }
}