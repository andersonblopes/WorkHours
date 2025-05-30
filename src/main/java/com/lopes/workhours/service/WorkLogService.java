package com.lopes.workhours.service;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.domain.repository.WorkLogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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
            workLog.setCurrencyUnitValue(workLog.getApartment().getCurrencyValue());
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

    public Page<WorkLog> findByFilter(WorkLogFilter filter, Pageable pageable) {
        return repository.findByFilter(filter, pageable);
    }

    public BigDecimal sumCurrency(List<WorkLog> logs) {
        if (logs == null || logs.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return logs.stream()
                .filter(log -> log.getTotal() != null)
                .map(WorkLog::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Long sumHours(List<WorkLog> logs) {
        if (logs == null || logs.isEmpty()) {
            return 0L;
        }

        return logs.stream()
                .map(WorkLog::getDuration)
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();
    }

}
