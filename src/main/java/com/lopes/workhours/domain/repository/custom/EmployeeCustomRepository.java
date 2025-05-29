package com.lopes.workhours.domain.repository.custom;

import com.lopes.workhours.domain.entities.Employee;
import com.lopes.workhours.domain.filter.EmployeeFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeCustomRepository {

    Page<Employee> findByFilter(EmployeeFilter filter, Pageable pageable);

    Long countByFilter(EmployeeFilter filter);
}
