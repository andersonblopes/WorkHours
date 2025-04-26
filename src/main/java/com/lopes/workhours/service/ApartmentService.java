package com.lopes.workhours.service;

import com.lopes.workhours.domain.entities.Apartment;
import com.lopes.workhours.domain.repository.ApartmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class ApartmentService {

    private final ApartmentRepository repository;
    private final BuildService buildService;

    public List<Apartment> findAll() {
        return repository.findAll();
    }

    public Apartment save(Apartment entity) {

        if (nonNull(entity.getBuild())) {
            entity.setBuild(buildService.findById(entity.getBuild().getId()));
        }

        return repository.save(entity);
    }

    public Apartment findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Apartment with ID " + id + " not found"));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
