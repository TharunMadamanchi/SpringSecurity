package com.maheswara.employeeRegistrationForm.Repository;

import com.maheswara.employeeRegistrationForm.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
