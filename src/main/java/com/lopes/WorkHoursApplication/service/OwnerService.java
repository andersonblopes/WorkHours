package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.entities.Owner;
import com.lopes.WorkHoursApplication.domain.repository.OwnerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Service
public class OwnerService {

    private final OwnerRepository repository;
    private final UserService userService;

    public List<Owner> getAll() {
        return repository.findAll();
    }

    public Owner save(Owner entity) {

        if (nonNull(entity.getUser())) {
            entity.setUser(userService.findById(entity.getUser().getId()));
        }

        return repository.save(entity);
    }

    public Owner findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Owner with ID " + id + " not found"));
    }

}
