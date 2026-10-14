package com.example.dossier.controller;

import com.example.dossier.entity.User;
import com.example.dossier.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "https://the-dossier-front-end.vercel.app"
})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);

        return ResponseEntity.ok(Map.of(
                "message", "Registration successful",
                "username", registeredUser.getUsername(),
                "email", registeredUser.getEmail(),
                "role", registeredUser.getRole() == null
                        ? "INVESTIGATOR"
                        : registeredUser.getRole()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> request) {

        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null
                || username.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest().body(
                    Map.of("message", "Username and password are required")
            );
        }

        Optional<User> userOptional =
                userService.loginUser(username, password);

        if (userOptional.isPresent()) {
            User user = userOptional.get();

            return ResponseEntity.ok(Map.of(
                    "message", "Login successful",
                    "username", user.getUsername(),
                    "role", user.getRole() == null
                            ? "INVESTIGATOR"
                            : user.getRole()
            ));
        }

        return ResponseEntity.status(401).body(
                Map.of("message", "Invalid username or password")
        );
    }
}