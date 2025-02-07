package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.UserDetailsImpl;
import com.lopes.WorkHoursApplication.domain.entities.User;
import com.lopes.WorkHoursApplication.domain.entities.WorkLog;
import com.lopes.WorkHoursApplication.domain.repository.UserRepository;
import com.lopes.WorkHoursApplication.domain.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private  final UserService userService;

    public List<WorkLog> getAllLogs() {
        return workLogRepository.findAll();
    }

    public WorkLog saveLog(WorkLog workLog) {

        workLog.setUser(userService.getAuthenticatedUser());

        return workLogRepository.save(workLog);
    }
}