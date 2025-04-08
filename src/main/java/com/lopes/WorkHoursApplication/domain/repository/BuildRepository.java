package com.lopes.WorkHoursApplication.domain.repository;

import com.lopes.WorkHoursApplication.domain.entities.Build;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildRepository extends JpaRepository<Build, Long> {
}
