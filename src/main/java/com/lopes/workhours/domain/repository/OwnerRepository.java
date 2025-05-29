package com.lopes.workhours.domain.repository;

import com.lopes.workhours.domain.entities.Owner;
import com.lopes.workhours.domain.repository.custom.OwnerCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long>, OwnerCustomRepository {
}
