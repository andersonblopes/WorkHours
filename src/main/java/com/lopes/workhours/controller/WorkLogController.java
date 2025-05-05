package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.WorkLog;
import com.lopes.workhours.domain.filter.WorkLogFilter;
import com.lopes.workhours.service.ApartmentService;
import com.lopes.workhours.service.EmployeeService;
import com.lopes.workhours.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Controller
@RequestMapping("/log")
public class WorkLogController {

    private final WorkLogService service;
    private final ApartmentService apartmentService;
    private final EmployeeService employeeService;

    @GetMapping
    public String getLogs(final @ModelAttribute WorkLogFilter filter,
                          final Pageable pageable,
                          final Model model) {

        populateModel(model);

        // Default to last 30 days if no dates are set
        if (filter.getStartDate() == null && filter.getEndDate() == null) {
            filter.setEndDate(LocalDate.now());
            filter.setStartDate(LocalDate.now().minusDays(30));
        }

        Page<WorkLog> logPage = service.findByFilter(filter, pageable);
        model.addAttribute("logs", logPage);
        model.addAttribute("filter", filter);
        model.addAttribute("totalCurrency", service.sumCurrency(logPage.getContent()));
        model.addAttribute("totalHours", service.sumHours(logPage.getContent()));

        return "pages/log/log";
    }

    @GetMapping("/add")
    public String add(final Model model) {
        populateModel(model);

        return "pages/log/add-log";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam(value = "id", required = false) final Long id,
            @RequestParam("executionDate") final LocalDateTime executionDate,
            @RequestParam("employeeId") final Long employeeId,
            @RequestParam("apartmentId") final Long apartmentId,
            @RequestParam("duration") final Long duration,
            @RequestParam("notes") final String notes) {

        final var apartment = apartmentService.findById(apartmentId);
        final var employee = employeeService.findById(employeeId);

        final var log = (id != null) ? service.findById(id) : new WorkLog();

        log.setExecutionDate(executionDate);
        log.setDuration(duration);
        log.setApartment(apartment);
        log.setEmployee(employee);
        log.setNotes(notes);

        service.save(log);

        return "redirect:/log";
    }

    @GetMapping("/edit/{id}")
    public String editEntity(@PathVariable final Long id, final Model model) {
        final var entity = service.findById(id);
        populateModel(model);
        model.addAttribute("log", entity);

        return "pages/log/add-log";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);

        return "redirect:/log";
    }

    private void populateModel(final Model model) {
        model.addAttribute("apartments", apartmentService.findAll());
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("contextPath", "/work-logs");
    }

}
