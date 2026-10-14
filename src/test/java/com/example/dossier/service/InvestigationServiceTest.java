package com.example.dossier.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.example.dossier.entity.Case;
import com.example.dossier.entity.Investigation;
import com.example.dossier.entity.User;
import com.example.dossier.repository.CaseRepository;
import com.example.dossier.repository.InvestigationRepository;
import com.example.dossier.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class InvestigationServiceTest {

    @Mock
    private InvestigationRepository investigationRepository;

    @Mock
    private CaseRepository caseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private InvestigationService investigationService;

    @Test
    void submitsTheoryForAuthenticatedInvestigatorAndSelectedCase() {
        User investigator = new User();
        investigator.setId(4L);
        investigator.setUsername("signed-in-investigator");
        investigator.setRole("INVESTIGATOR");

        Case investigationCase = new Case();
        investigationCase.setId(12L);

        when(userRepository.findByUsername("signed-in-investigator"))
                .thenReturn(Optional.of(investigator));
        when(caseRepository.findById(12L))
                .thenReturn(Optional.of(investigationCase));
        when(investigationRepository.save(any(Investigation.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Investigation saved = investigationService.submitInvestigation(
                "  The witness had a motive.  ",
                12L,
                "signed-in-investigator"
        );

        assertEquals("The witness had a motive.", saved.getTheory());
        assertSame(investigationCase, saved.getInvestigationCase());
        assertSame(investigator, saved.getInvestigator());
    }

    @Test
    void rejectsCaseCreatorsAndDoesNotPersistTheirSubmission() {
        User caseCreator = new User();
        caseCreator.setRole("CASE_CREATOR");
        when(userRepository.findByUsername("creator"))
                .thenReturn(Optional.of(caseCreator));

        assertThrows(
                ResponseStatusException.class,
                () -> investigationService.submitInvestigation(
                        "A theory",
                        12L,
                        "creator"
                )
        );

        verifyNoInteractions(caseRepository, investigationRepository);
    }
}
