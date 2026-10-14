package com.example.dossier.controller;

import java.time.LocalDateTime;

public record InvestigationSubmissionResponse(
        Long id,
        String theory,
        String status,
        LocalDateTime submittedAt,
        Long caseId) {
}
