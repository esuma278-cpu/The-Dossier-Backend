package com.example.dossier.service;

import com.example.dossier.entity.Case;
import com.example.dossier.repository.CaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public Case createCase(Case investigationCase) {
        return caseRepository.save(investigationCase);
    }

    public List<Case> getAllCases() {
        return caseRepository.findAll();
    }

    public Case getCaseById(Long id) {
        return caseRepository.findById(id).orElse(null);
    }

    public Case updateCase(Long id, Case updatedCase) {
        Case existingCase = caseRepository.findById(id).orElse(null);

        if (existingCase == null) {
            return null;
        }

        existingCase.setTitle(updatedCase.getTitle());
        existingCase.setDescription(updatedCase.getDescription());
        existingCase.setDifficulty(updatedCase.getDifficulty());
        existingCase.setStatus(updatedCase.getStatus());

        return caseRepository.save(existingCase);
    }

    public boolean deleteCase(Long id) {
        if (!caseRepository.existsById(id)) {
            return false;
        }

        caseRepository.deleteById(id);
        return true;
    }
}