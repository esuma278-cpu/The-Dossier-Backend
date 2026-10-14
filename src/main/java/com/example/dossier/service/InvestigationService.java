package com.example.dossier.service;

import com.example.dossier.entity.Investigation;
import com.example.dossier.entity.Case;
import com.example.dossier.entity.User;
import com.example.dossier.repository.CaseRepository;
import com.example.dossier.repository.InvestigationRepository;
import com.example.dossier.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class InvestigationService {

    private final InvestigationRepository investigationRepository;
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    public InvestigationService(
            InvestigationRepository investigationRepository,
            CaseRepository caseRepository,
            UserRepository userRepository) {
        this.investigationRepository = investigationRepository;
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
    }

    public Investigation submitInvestigation(
            String theory,
            Long caseId,
            String authenticatedUsername) {
        User investigator = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Authenticated user no longer exists."
                ));

        String role = investigator.getRole() == null
                ? "INVESTIGATOR"
                : investigator.getRole();
        if (!"INVESTIGATOR".equals(role)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Only investigators can submit theories."
            );
        }

        Case investigationCase = caseRepository.findById(caseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "The selected case was not found."
                ));

        Investigation investigation = new Investigation();
        investigation.setTheory(theory.trim());
        investigation.setInvestigationCase(investigationCase);
        investigation.setInvestigator(investigator);
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