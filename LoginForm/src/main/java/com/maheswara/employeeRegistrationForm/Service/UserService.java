package com.maheswara.employeeRegistrationForm.Service;

import com.maheswara.employeeRegistrationForm.Model.User;
import com.maheswara.employeeRegistrationForm.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    private static final int MAX_ATTEMPTS = 3;

    public void loginFailed(String username) {
        repo.findByUsername(username).ifPresent(u -> {
            int attempts = u.getFailedAttempts() + 1;
            u.setFailedAttempts(attempts);
            if (attempts >= MAX_ATTEMPTS) u.setAccountNonLocked(false);
            repo.save(u);
        });
    }

    public void loginSucceeded(String username) {
        repo.findByUsername(username).ifPresent(u -> {
            u.setFailedAttempts(0);
            u.setAccountNonLocked(true);
            u.setLastLoginAt(LocalDateTime.now());
            repo.save(u);
        });
    }

    public void recordLogout(String username) {
        repo.findByUsername(username).ifPresent(u -> {
            u.setLastLogoutAt(LocalDateTime.now());
            repo.save(u);
        });
    }
}
