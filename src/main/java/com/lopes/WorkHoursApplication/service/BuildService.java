package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.entities.Build;
import com.lopes.WorkHoursApplication.domain.repository.BuildRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class BuildService {

    private final BuildRepository repository;
    private final OwnerService ownerService;

    public List<Build> getAll() {
        return repository.findAll();
    }

    public Build save(Build entity) {

        if (nonNull(entity.getOwner())) {
            entity.setOwner(ownerService.findById(entity.getOwner().getId()));
        }

        return repository.save(entity);
    }

    public Build findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Build with ID " + id + " not found"));
    }
}
