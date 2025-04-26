package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/home")
public class HomeController {

    private final WorkLogService logService;

    @GetMapping()
    public String home(final Model model) {
        List<WorkLog> workLogs = logService.getAll();

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

        // Example of workLogsOverTime
        Map<String, Long> workLogsOverTime = new LinkedHashMap<>();
        workLogsOverTime.put("2025-04-20", 5L);
        workLogsOverTime.put("2025-04-21", 8L);
        workLogsOverTime.put("2025-04-22", 4L);
        workLogsOverTime.put("2025-04-23", 9L);

        model.addAttribute("workLogsOverTime", workLogsOverTime);

        return "home"; // Return the name of the Thymeleaf template for the home page
    }
}
