package com.lopes.WorkHoursApplication.domain.repository;

import com.lopes.WorkHoursApplication.domain.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
