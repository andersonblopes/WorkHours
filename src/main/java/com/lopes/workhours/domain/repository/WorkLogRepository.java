package com.lopes.workhours.domain.repository;

import com.lopes.workhours.domain.entities.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
}
