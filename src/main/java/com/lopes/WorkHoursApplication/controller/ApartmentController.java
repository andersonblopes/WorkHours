package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.Apartment;
import com.lopes.WorkHoursApplication.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller
public class ApartmentController {

    private final ApartmentService service;

    @GetMapping("/apartment")
    public String getAll(Model model) {
        model.addAttribute("apartments", service.getAll());
        return "pages/apartment/apartment";
    }

    @GetMapping("/apartment/add")
    public String add() {
        return "pages/apartment/add-apartment";
    }

    @PostMapping("/apartment/save")
    public String save(
            @RequestParam("description") String description,
            @RequestParam("accessCode") String accessCode,
            @RequestParam("currencyValue") String currencyValue,
            @RequestParam(value = "buildId", required = false) Long buildId) {

        Apartment apartment = Apartment.builder()
                .description(description)
                .accessCode(accessCode)
                .build();

        if (currencyValue != null && !currencyValue.isBlank()) {
            String normalized = currencyValue.replace(",", ".");
            apartment.setCurrencyValue(new BigDecimal(normalized));
        }

        service.save(apartment);

        return "redirect:/apartment";
    }

}
