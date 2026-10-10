package com.example.dossier.controller;

import com.example.dossier.entity.Case;
import com.example.dossier.service.CaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cases")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://the-dossier-front-end.vercel.app"
})
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @PostMapping
    public Case createCase(@RequestBody Case investigationCase) {
        return caseService.createCase(investigationCase);
    }

    @GetMapping
    public List<Case> getAllCases() {
        return caseService.getAllCases();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Case> getCaseById(@PathVariable Long id) {
        Case investigationCase = caseService.getCaseById(id);

        if (investigationCase == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(investigationCase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Case> updateCase(
            @PathVariable Long id,
            @RequestBody Case updatedCase) {

        Case investigationCase = caseService.updateCase(id, updatedCase);

        if (investigationCase == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(investigationCase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCase(@PathVariable Long id) {
        boolean deleted = caseService.deleteCase(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}