package com.lopes.workhours.service;

import com.lopes.workhours.domain.entities.Employee;
import com.lopes.workhours.domain.filter.EmployeeFilter;
import com.lopes.workhours.domain.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> findAll() {
        return repository.findAll();
    }

    public Page<Employee> findByFilter(EmployeeFilter filter, Pageable pageable) {
        return repository.findByFilter(filter, pageable);
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee with ID " + id + " not found"));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
