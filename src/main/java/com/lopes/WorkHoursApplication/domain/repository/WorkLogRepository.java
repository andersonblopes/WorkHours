package com.lopes.WorkHoursApplication.domain.repository;

import com.lopes.WorkHoursApplication.domain.entities.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
}