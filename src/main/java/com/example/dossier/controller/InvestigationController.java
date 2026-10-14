package com.example.dossier.controller;

import com.example.dossier.entity.Investigation;
import com.example.dossier.service.InvestigationService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investigations")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://the-dossier-front-end.vercel.app"
})
public class InvestigationController {

    private final InvestigationService investigationService;

    public InvestigationController(InvestigationService investigationService) {
        this.investigationService = investigationService;
    }

    @PostMapping
    public InvestigationSubmissionResponse submitInvestigation(
            @Valid @RequestBody InvestigationSubmissionRequest request,
            Authentication authentication) {
        Investigation saved = investigationService.submitInvestigation(
                request.theory(),
                request.caseId(),
                authentication.getName()
        );
        return new InvestigationSubmissionResponse(
                saved.getId(),
                saved.getTheory(),
                saved.getStatus(),
                saved.getSubmittedAt(),
                saved.getInvestigationCase().getId()
        );
    }

    @GetMapping
    public List<Investigation> getAllInvestigations() {
        return investigationService.getAllInvestigations();
    }

    @GetMapping("/{id}")
    public Investigation getInvestigationById(@PathVariable Long id) {
        return investigationService.getInvestigationById(id);
    }

    @GetMapping("/case/{caseId}")
    public List<Investigation> getInvestigationsByCase(
            @PathVariable Long caseId) {
        return investigationService.getInvestigationsByCase(caseId);
    }

    @GetMapping("/investigator/{investigatorId}")
    public List<Investigation> getInvestigationsByInvestigator(
            @PathVariable Long investigatorId) {
        return investigationService.getInvestigationsByInvestigator(
                investigatorId);
    }
}