package com.example.dossier.controller;

import com.example.dossier.entity.Evidence;
import com.example.dossier.service.EvidenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evidence")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://the-dossier-front-end.vercel.app"
})
public class EvidenceController {

    private final EvidenceService evidenceService;

    public EvidenceController(EvidenceService evidenceService) {
        this.evidenceService = evidenceService;
    }

    @PostMapping
    public Evidence addEvidence(@RequestBody Evidence evidence) {
        return evidenceService.addEvidence(evidence);
    }

    @GetMapping
    public List<Evidence> getAllEvidence() {
        return evidenceService.getAllEvidence();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evidence> getEvidenceById(@PathVariable Long id) {
        Evidence evidence = evidenceService.getEvidenceById(id);

        if (evidence == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(evidence);
    }

    @GetMapping("/case/{caseId}")
    public List<Evidence> getEvidenceByCase(@PathVariable Long caseId) {
        return evidenceService.getEvidenceByCase(caseId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evidence> updateEvidence(
            @PathVariable Long id,
            @RequestBody Evidence updatedEvidence) {

        Evidence evidence = evidenceService.updateEvidence(id, updatedEvidence);

        if (evidence == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(evidence);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvidence(@PathVariable Long id) {
        boolean deleted = evidenceService.deleteEvidence(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}