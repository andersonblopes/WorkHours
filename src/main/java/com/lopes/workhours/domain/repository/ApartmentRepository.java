package com.lopes.workhours.domain.repository;

import com.lopes.workhours.domain.entities.Apartment;
import com.lopes.workhours.domain.repository.custom.ApartmentCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long>, ApartmentCustomRepository {
}
