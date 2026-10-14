package com.example.dossier.service;

import com.example.dossier.entity.User;
import com.example.dossier.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(User user) {
        String requestedRole = user.getRole();
        String role = requestedRole == null || requestedRole.isBlank()
                ? "INVESTIGATOR"
                : requestedRole.trim().toUpperCase(Locale.ROOT);

        if (!role.equals("INVESTIGATOR") && !role.equals("CASE_CREATOR")) {
            throw new IllegalArgumentException(
                    "Administrator accounts are provisioned privately."
            );
        }

        user.setRole(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> userOptional =
                userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(user);
            }
        }

        return Optional.empty();
    }
}