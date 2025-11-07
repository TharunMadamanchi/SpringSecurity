package com.maheswara.employeeRegistrationForm.Controller;

import com.maheswara.employeeRegistrationForm.DTO.UserDTO;
import com.maheswara.employeeRegistrationForm.DTO.UserLoginDTO;
import com.maheswara.employeeRegistrationForm.Model.User;
import com.maheswara.employeeRegistrationForm.Repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        User user = new User();

        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(userDTO.getRole());
        user.setAccountNonLocked(true);
        user.setFailedAttempts(0);
 user.setLastLoginAt(LocalDateTime.now());
        user.setLastLogoutAt(LocalDateTime.now());

        repo.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserLoginDTO loginDTO) {
        Optional<User> optionalUser = repo.findByUsername(loginDTO.getUsername());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        user.setLastLoginAt(LocalDateTime.now());
        repo.save(user);

        return ResponseEntity.ok("Login successful");
    }

    @GetMapping("/status")
    public String status() {
        return "Authentication service is running!";
    }
}