package com.lopes.workhours.domain.repository;

import com.lopes.workhours.domain.entities.Build;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildRepository extends JpaRepository<Build, Long> {
}
