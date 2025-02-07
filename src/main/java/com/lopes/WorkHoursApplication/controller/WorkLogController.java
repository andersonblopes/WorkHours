package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.WorkLog;
import com.lopes.WorkHoursApplication.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
public class WorkLogController {

    private final WorkLogService workLogService;

    @GetMapping("/worklogs")
    public String getWorklogs(Model model) {
        model.addAttribute("worklogs", workLogService.getAllLogs());
        return "worklogs";
    }

    @GetMapping("/worklogs/add")
    public String addWorklogForm() {
        return "add-worklog";
    }

    @PostMapping("/worklogs/save")
    public String saveWorklog(
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime,
            @RequestParam Long duration) {

        // TODO DTO must be used

        WorkLog log = WorkLog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .duration(duration) // TODO Duration must be calculated
                .build();

        workLogService.saveLog(log);

        return "redirect:/worklogs";
    }

}
