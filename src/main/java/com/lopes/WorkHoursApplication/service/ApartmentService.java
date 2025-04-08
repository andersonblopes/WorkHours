package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.entities.Apartment;
import com.lopes.WorkHoursApplication.domain.repository.ApartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApartmentService {

    private final ApartmentRepository repository;
    private final BuildService buildService;

    public List<Apartment> getAll() {
        return repository.findAll();
    }

    public Apartment save(Apartment entity) {

        if (nonNull(entity.getBuild())) {
            entity.setBuild(buildService.findById(entity.getBuild().getId()));
        }

        return repository.save(entity);
    }
}
