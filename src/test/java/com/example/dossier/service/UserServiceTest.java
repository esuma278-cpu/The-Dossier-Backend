package com.example.dossier.service;

import com.example.dossier.entity.User;
import com.example.dossier.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void registersCaseCreatorProfiles() {
        User user = new User();
        user.setUsername("creator");
        user.setPassword("password");
        user.setRole("case_creator");

        when(passwordEncoder.encode("password")).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertEquals("CASE_CREATOR", registeredUser.getRole());
        assertEquals("encoded", registeredUser.getPassword());
    }

    @Test
    void rejectsAdministratorSelfRegistration() {
        User user = new User();
        user.setRole("administrator");

        assertThrows(
                IllegalArgumentException.class,
                () -> userService.registerUser(user)
        );
        verifyNoInteractions(passwordEncoder, userRepository);
    }
}
