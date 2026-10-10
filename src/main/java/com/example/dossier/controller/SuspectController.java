package com.example.dossier.controller;

import com.example.dossier.entity.Suspect;
import com.example.dossier.service.SuspectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suspects")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://the-dossier-front-end.vercel.app"
})
public class SuspectController {

    private final SuspectService suspectService;

    public SuspectController(SuspectService suspectService) {
        this.suspectService = suspectService;
    }

    @PostMapping
    public Suspect addSuspect(@RequestBody Suspect suspect) {
        return suspectService.addSuspect(suspect);
    }

    @GetMapping
    public List<Suspect> getAllSuspects() {
        return suspectService.getAllSuspects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Suspect> getSuspectById(@PathVariable Long id) {
        Suspect suspect = suspectService.getSuspectById(id);

        if (suspect == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(suspect);
    }

    @GetMapping("/case/{caseId}")
    public List<Suspect> getSuspectsByCase(@PathVariable Long caseId) {
        return suspectService.getSuspectsByCase(caseId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Suspect> updateSuspect(
            @PathVariable Long id,
            @RequestBody Suspect updatedSuspect) {

        Suspect suspect = suspectService.updateSuspect(id, updatedSuspect);

        if (suspect == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(suspect);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuspect(@PathVariable Long id) {
        boolean deleted = suspectService.deleteSuspect(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}