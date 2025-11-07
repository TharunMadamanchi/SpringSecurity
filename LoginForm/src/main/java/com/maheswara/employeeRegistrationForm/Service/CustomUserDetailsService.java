package com.maheswara.employeeRegistrationForm.Service;

import com.maheswara.employeeRegistrationForm.Model.User;
import com.maheswara.employeeRegistrationForm.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        String role = u.getRole().startsWith("ROLE_") ? u.getRole() : "ROLE_" + u.getRole();

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(),
                u.getPassword(),
                u.isAccountNonLocked(),
                true,
                true,
                true,
                List.of(new SimpleGrantedAuthority(role))
        );
    }
}
