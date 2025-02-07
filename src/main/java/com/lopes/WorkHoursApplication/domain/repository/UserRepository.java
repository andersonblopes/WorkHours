package com.lopes.WorkHoursApplication.domain.repository;

import com.lopes.WorkHoursApplication.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}