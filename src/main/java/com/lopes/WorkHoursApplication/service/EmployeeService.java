package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.entities.Employee;
import com.lopes.WorkHoursApplication.domain.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));
    }
}
