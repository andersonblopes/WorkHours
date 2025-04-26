package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/log")
public class LogController {

    private final WorkLogService service;

    @GetMapping()
    public String getLogs(final Model model) {
        model.addAttribute("logs", service.getAll());

        return "pages/log/log";
    }

    @GetMapping("/add")
    public String add() {
        return "pages/log/add-log";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam("executionDate") LocalDateTime executionDate,
            @RequestParam Long duration) {

        WorkLog log = WorkLog.builder()
                .executionDate(executionDate)
                .duration(duration)
                .build();

        service.save(log);

        return "redirect:/pages/log/log";
    }

    @GetMapping("/edit/{id}")
    public String editEntity(@PathVariable final Long id, final Model model) {
        final var entity = service.findById(id);
        model.addAttribute("log", entity);

        return "pages/log/add-log";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);

        return "redirect:/log";
    }

}
