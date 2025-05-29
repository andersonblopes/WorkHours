package com.lopes.workhours.domain.repository;

import com.lopes.workhours.domain.entities.Employee;
import com.lopes.workhours.domain.repository.custom.EmployeeCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeCustomRepository {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByNickName(String nickName);

    Optional<Employee> findByUserId(Long id);
}
