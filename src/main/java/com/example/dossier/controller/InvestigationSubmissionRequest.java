package com.example.dossier.controller;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InvestigationSubmissionRequest(
        @NotBlank String theory,
        @NotNull @Positive Long caseId) {
}
