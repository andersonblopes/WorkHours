package com.lopes.WorkHoursApplication.domain.repository;

import com.lopes.WorkHoursApplication.domain.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByNickName(String nickName);

    Optional<Employee> findByUserId(Long id);
}
