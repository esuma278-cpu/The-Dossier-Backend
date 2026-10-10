package com.example.dossier.service;

import com.example.dossier.entity.Suspect;
import com.example.dossier.repository.SuspectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuspectService {

    private final SuspectRepository suspectRepository;

    public SuspectService(SuspectRepository suspectRepository) {
        this.suspectRepository = suspectRepository;
    }

    public Suspect addSuspect(Suspect suspect) {
        return suspectRepository.save(suspect);
    }

    public List<Suspect> getAllSuspects() {
        return suspectRepository.findAll();
    }

    public Suspect getSuspectById(Long id) {
        return suspectRepository.findById(id).orElse(null);
    }

    public List<Suspect> getSuspectsByCase(Long caseId) {
        return suspectRepository.findByInvestigationCase_Id(caseId);
    }

    public Suspect updateSuspect(Long id, Suspect updatedSuspect) {
        Suspect existingSuspect = suspectRepository.findById(id).orElse(null);

        if (existingSuspect == null) {
            return null;
        }

        existingSuspect.setName(updatedSuspect.getName());
        existingSuspect.setDescription(updatedSuspect.getDescription());
        existingSuspect.setOccupation(updatedSuspect.getOccupation());
        existingSuspect.setStatus(updatedSuspect.getStatus());

        return suspectRepository.save(existingSuspect);
    }

    public boolean deleteSuspect(Long id) {
        if (!suspectRepository.existsById(id)) {
            return false;
        }

        suspectRepository.deleteById(id);
        return true;
    }
}