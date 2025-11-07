package com.maheswara.employeeRegistrationForm.Controller;

import com.maheswara.employeeRegistrationForm.DTO.UserDTO;
import com.maheswara.employeeRegistrationForm.DTO.UserLoginDTO;
import com.maheswara.employeeRegistrationForm.Model.User;
import com.maheswara.employeeRegistrationForm.Repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setAccountNonLocked(true);
        user.setFailedAttempts(0);
        user.setLastLoginAt(LocalDateTime.now());
        user.setLastLogoutAt(LocalDateTime.now());

        repo.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody UserLoginDTO loginDTO) {
        Optional<User> optionalUser = repo.findByUsername(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }

        User user = optionalUser.get();

        // Check if account is locked
        if (!user.isAccountNonLocked()) {
            return ResponseEntity.status(403).body(Map.of("message", "User is blocked. Max attempts reached."));
        }

        //  Validate password
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            int attempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(attempts);

            if (attempts >= 3) {
                user.setAccountNonLocked(false);
                repo.save(user);
                return ResponseEntity.status(403).body(Map.of("message", "User is blocked. Max attempts reached."));
            }

            repo.save(user);
            return ResponseEntity.status(401).body(Map.of("message", "Invalid username or password"));
        }

        //  Successful login
        user.setFailedAttempts(0);
        user.setLastLoginAt(LocalDateTime.now());
        repo.save(user);

        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of("message", "Authentication service is running!");
    }
}
