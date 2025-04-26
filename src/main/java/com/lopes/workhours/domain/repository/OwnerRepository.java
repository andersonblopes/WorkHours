package com.lopes.workhours.domain.repository;

import com.lopes.workhours.domain.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
