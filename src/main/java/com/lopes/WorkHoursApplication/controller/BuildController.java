package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.Build;
import com.lopes.WorkHoursApplication.domain.entities.Owner;
import com.lopes.WorkHoursApplication.service.BuildService;
import com.lopes.WorkHoursApplication.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BuildController {

    private final BuildService service;
    private final OwnerService ownerService;

    @GetMapping("/build")
    public String getAll(Model model) {
        model.addAttribute("builds", service.getAll());
        return "pages/build/build";
    }

    @GetMapping("/build/add")
    public String showAddBuildForm(Model model) {
        List<Owner> owners = ownerService.findAll();
        model.addAttribute("owners", owners);
        return "pages/build/add-build";
    }

    @PostMapping("/build/save")
    public String save(
            @RequestParam("description") String description,
            @RequestParam("address") String address,
            @RequestParam("accessCode") String accessCode,
            @RequestParam("ownerId") Long ownerId) {

        Build owner = Build.builder()
                .description(description)
                .address(address)
                .accessCode(accessCode)
                .owner(ownerService.findById(ownerId))
                .build();

        service.save(owner);

        return "redirect:/pages/build/build";
    }

}
