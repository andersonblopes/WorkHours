package com.lopes.workhours.domain.repository;

import com.lopes.workhours.domain.entities.Build;
import com.lopes.workhours.domain.repository.custom.BuildCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildRepository extends JpaRepository<Build, Long>, BuildCustomRepository {
}
