package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class HomeController {

    private final WorkLogService service;

    @GetMapping()
    public String home(final @ModelAttribute WorkLogFilter filter, final Pageable pageable,
                       final Model model) {

        // Default to last 30 days if no dates are set
        if (filter.getStartDate() == null && filter.getEndDate() == null) {
            filter.setEndDate(LocalDate.now());
            filter.setStartDate(LocalDate.now().minusDays(30));
        }

        Page<WorkLog> logPage = service.findByFilter(filter, pageable);
        List<WorkLog> workLogs = logPage.getContent();
        model.addAttribute("filter", filter);

        Map<String, Long> durationPerEmployee = workLogs.stream()
                .collect(Collectors.groupingBy(
                        wl -> wl.getEmployee().getName(),
                        Collectors.summingLong(WorkLog::getDuration)
                ));

        model.addAttribute("durationPerEmployee", durationPerEmployee);

        // Count how many work logs per apartment
        Map<String, Long> workLogsPerApartment = workLogs.stream()
                .collect(Collectors.groupingBy(
                        wl -> wl.getApartment().descriptionFormated(),
                        Collectors.counting()
                ));

        model.addAttribute("workLogsPerApartment", workLogsPerApartment);

        Map<String, Long> workLogsOverTime = workLogs.stream()
                .collect(Collectors.groupingBy(
                        wl -> wl.getExecutionDate().toLocalDate().toString(), // Only date, e.g., "2025-04-21"
                        LinkedHashMap::new, // Preserves insertion order
                        Collectors.counting()
                ));

        model.addAttribute("workLogsOverTime", workLogsOverTime);

        return "home";
    }
}
