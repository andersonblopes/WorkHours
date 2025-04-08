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
public class LogController {

    private final WorkLogService workLogService;

    @GetMapping("/log")
    public String getLogs(Model model) {
        model.addAttribute("logs", workLogService.getAll());
        return "pages/log/log";
    }

    @GetMapping("/log/add")
    public String add() {
        return "pages/log/add-log";
    }

    @PostMapping("/log/save")
    public String save(
            @RequestParam("startTime") LocalDateTime startTime,
            @RequestParam("endTime") LocalDateTime endTime,
            @RequestParam Long duration) {

        WorkLog log = WorkLog.builder()
                .startTime(startTime)
                .endTime(endTime)
                .duration(duration)
                .build();

        workLogService.saveLog(log);

        return "redirect:/pages/log/log";
    }

}
