package com.example.dossier.service;

import com.example.dossier.entity.Evidence;
import com.example.dossier.repository.EvidenceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvidenceService {

    private final EvidenceRepository evidenceRepository;

    public EvidenceService(EvidenceRepository evidenceRepository) {
        this.evidenceRepository = evidenceRepository;
    }

    public Evidence addEvidence(Evidence evidence) {
        return evidenceRepository.save(evidence);
    }

    public List<Evidence> getAllEvidence() {
        return evidenceRepository.findAll();
    }

    public Evidence getEvidenceById(Long id) {
        return evidenceRepository.findById(id).orElse(null);
    }

    public List<Evidence> getEvidenceByCase(Long caseId) {
        return evidenceRepository.findByInvestigationCase_Id(caseId);
    }

    public Evidence updateEvidence(Long id, Evidence updatedEvidence) {
        Evidence existingEvidence =
                evidenceRepository.findById(id).orElse(null);

        if (existingEvidence == null) {
            return null;
        }

        existingEvidence.setTitle(updatedEvidence.getTitle());
        existingEvidence.setDescription(updatedEvidence.getDescription());
        existingEvidence.setType(updatedEvidence.getType());
        existingEvidence.setFileUrl(updatedEvidence.getFileUrl());

        return evidenceRepository.save(existingEvidence);
    }

    public boolean deleteEvidence(Long id) {
        if (!evidenceRepository.existsById(id)) {
            return false;
        }

        evidenceRepository.deleteById(id);
        return true;
    }
}