package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.Apartment;
import com.lopes.workhours.service.ApartmentService;
import com.lopes.workhours.service.BuildService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller
public class ApartmentController {

    private final ApartmentService service;
    private final BuildService buildService;

    @GetMapping("/apartment")
    public String getAll(final Model model) {
        model.addAttribute("apartments", service.getAll());
        return "pages/apartment/apartment";
    }

    @GetMapping("/apartment/add")
    public String add(final Model model) {
        model.addAttribute("builds", buildService.findAll());
        return "pages/apartment/add-apartment";
    }

    @PostMapping("/apartment/save")
    public String save(
            @RequestParam("description") final String description,
            @RequestParam("accessCode") final String accessCode,
            @RequestParam("currencyValue") final String currencyValue,
            @RequestParam(value = "buildId") final Long buildId) {

        final var apartment = Apartment.builder()
                .description(description)
                .accessCode(accessCode)
                .build(buildService.findById(buildId))
                .build();

        if (currencyValue != null && !currencyValue.isBlank()) {
            String normalized = currencyValue.replace(",", ".");
            apartment.setCurrencyValue(new BigDecimal(normalized));
        }

        service.save(apartment);

        return "redirect:/apartment";
    }

    @GetMapping("/apartment/edit/{id}")
    public String editEntity(@PathVariable final Long id, final Model model) {
        final var entity = service.findById(id);
        model.addAttribute("apartment", entity);
        model.addAttribute("builds", buildService.findAll());

        return "pages/apartment/add-apartment";
    }

    @GetMapping("/apartment/delete/{id}")
    public String deleteEntity(@PathVariable final Long id) {
        service.deleteById(id);

        return "redirect:/apartment";
    }

}
