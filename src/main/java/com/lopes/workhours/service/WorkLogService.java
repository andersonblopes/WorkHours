package com.lopes.workhours.service;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.repository.WorkLogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class WorkLogService {

    private final WorkLogRepository repository;
    private final UserService userService;

    public List<WorkLog> getAll() {
        return repository.findAll();
    }

    @Transactional
    public WorkLog save(final WorkLog workLog) {

        if (isNull(workLog.getId())) {
            workLog.setCurrencyValue(workLog.getApartment().getCurrencyValue()
                    .multiply(new BigDecimal(workLog.getDuration())));
        }

        workLog.setUser(userService.getAuthenticatedUser());


        return repository.save(workLog);
    }

    public WorkLog findById(final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work log with ID " + id + " not found"));
    }

    @Transactional
    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
