package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.entities.WorkLog;
import com.lopes.WorkHoursApplication.domain.repository.WorkLogRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkLogService {

    private final WorkLogRepository repository;
    private final UserService userService;

    public List<WorkLog> getAll() {
        return repository.findAll();
    }

    public WorkLog save(WorkLog workLog) {

        workLog.setUser(userService.getAuthenticatedUser());

        return repository.save(workLog);
    }

    public WorkLog findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Work log with ID " + id + " not found"));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
