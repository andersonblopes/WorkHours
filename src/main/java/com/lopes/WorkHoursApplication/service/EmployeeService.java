package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.entities.Employee;
import com.lopes.WorkHoursApplication.domain.entities.WorkLog;
import com.lopes.WorkHoursApplication.domain.repository.EmployeeRepository;
import com.lopes.WorkHoursApplication.domain.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public List<Employee> getAllLogs() {
        return repository.findAll();
    }

    public Employee save(Employee employee) {
        return repository.save(employee);
    }
}
