package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.Apartment;
import com.lopes.workhours.domain.enums.DurationType;
import com.lopes.workhours.service.ApartmentService;
import com.lopes.workhours.service.BuildService;
import com.lopes.workhours.service.DurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/apartment")
public class ApartmentController {

    private final ApartmentService service;
    private final BuildService buildService;
    private final DurationService durationService;

    @GetMapping
    public String list(Model model, Locale locale) {
        model.addAttribute("apartments", service.getAll());
        model.addAttribute("durationDescriptions", getDurationDescriptions(locale));
        return "pages/apartment/apartment";
    }

    @GetMapping("/add")
    public String addForm(Model model, Locale locale) {
        populateFormModel(model, new Apartment(), locale);
        return "pages/apartment/add-apartment";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, Locale locale) {
        Apartment apartment = service.findById(id);
        populateFormModel(model, apartment, locale);
        return "pages/apartment/add-apartment";
    }

    @PostMapping("/save")
    public String save(
            @RequestParam(value = "id", required = false) final Long id,
            @RequestParam("description") final String description,
            @RequestParam("accessCode") final String accessCode,
            @RequestParam("currencyValue") final String currencyValue,
            @RequestParam("buildId") final Long buildId,
            @RequestParam("durationType") final DurationType durationType) {

        final Apartment apartment;

        if (id != null) {
            // EDIT: Find the existing apartment
            apartment = service.findById(id);
        } else {
            // ADD: Create a new one
            apartment = new Apartment();
        }

        apartment.setDescription(description);
        apartment.setAccessCode(accessCode);
        apartment.setDurationType(durationType);
        apartment.setBuild(buildService.findById(buildId));

        if (currencyValue != null && !currencyValue.isBlank()) {
            String normalized = currencyValue.replace(",", ".");
            apartment.setCurrencyValue(new BigDecimal(normalized));
        } else {
            apartment.setCurrencyValue(null);
        }

        service.save(apartment);

        return "redirect:/apartment";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/apartment";
    }

    // ---- Private helpers ----
    private void populateFormModel(Model model, Apartment apartment, Locale locale) {
        model.addAttribute("apartment", apartment);
        model.addAttribute("builds", buildService.findAll());
        model.addAttribute("durationTypes", DurationType.values());
        model.addAttribute("durationDescriptions", getDurationDescriptions(locale));
    }

    private Map<String, String> getDurationDescriptions(Locale locale) {
        return Arrays.stream(DurationType.values())
                .collect(Collectors.toMap(
                        Enum::name,
                        durationType -> durationService.getDescription(durationType, locale)
                ));
    }
}
