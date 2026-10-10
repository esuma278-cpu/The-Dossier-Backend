package com.example.dossier.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

@Entity
public class Investigation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theory;
    private String status;

    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Case investigationCase;

    @ManyToOne
    @JoinColumn(name = "investigator_id")
    private User investigator;

    public Investigation() {
    }

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();

        if (status == null) {
            status = "SUBMITTED";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTheory() {
        return theory;
    }

    public void setTheory(String theory) {
        this.theory = theory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public Case getInvestigationCase() {
        return investigationCase;
    }

    public void setInvestigationCase(Case investigationCase) {
        this.investigationCase = investigationCase;
    }

    public User getInvestigator() {
        return investigator;
    }

    public void setInvestigator(User investigator) {
        this.investigator = investigator;
    }
}