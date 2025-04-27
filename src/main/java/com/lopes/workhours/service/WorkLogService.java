package com.lopes.workhours.service;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.domain.repository.WorkLogRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public List<WorkLog> getAllFiltered(final WorkLogFilter filter) {
        List<WorkLog> logs = getAll();

        return logs.stream()
                .filter(log -> filter.getEmployeeNickname() == null
                        || log.getEmployee().getNickName().toLowerCase().contains(filter.getEmployeeNickname().toLowerCase()))
                .filter(log -> filter.getApartmentDesc() == null
                        || log.getApartment().getDescription().toLowerCase().contains(filter.getApartmentDesc().toLowerCase()))
                .filter(log -> {
                    if (isNull(filter.getStartDate()) && isNull(filter.getEndDate())) {
                        return true;
                    }
                    LocalDateTime date1 = LocalDateTime.parse(filter.getStartDate() + "T00:00:00");
                    LocalDateTime date2 = LocalDateTime.parse(filter.getEndDate() + "T23:59:59");
                    return log.getExecutionDate().isAfter(date1)
                            && log.getExecutionDate().isBefore(date2);
                })
                .toList();
    }
}
