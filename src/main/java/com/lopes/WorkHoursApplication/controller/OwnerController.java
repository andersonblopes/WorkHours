package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.Owner;
import com.lopes.WorkHoursApplication.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class OwnerController {

    private final OwnerService service;

    @GetMapping("/owner")
    public String getEmployees(Model model) {
        model.addAttribute("owners", service.getAll());
        return "pages/owner/owner";
    }

    @GetMapping("/owner/add")
    public String add() {
        return "pages/owner/add-owner";
    }

    @PostMapping("/owner/save")
    public String save(
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam("userId") Long userId) {

        Owner owner = Owner.builder()
                .name(name)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();

        service.save(owner);

        return "redirect:/pages/owner/owner";
    }

}
