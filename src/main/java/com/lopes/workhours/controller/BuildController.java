package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.Build;
import com.lopes.workhours.service.BuildService;
import com.lopes.workhours.service.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class BuildController {

    private final BuildService service;
    private final OwnerService ownerService;

    @GetMapping("/build")
    public String getAllEntities(final Model model) {
        model.addAttribute("builds", service.findAll());

        return "pages/build/build";
    }

    @GetMapping("/build/add")
    public String addEntity(final Model model) {
        model.addAttribute("owners", ownerService.findAll());

        return "pages/build/add-build";
    }

    @PostMapping("/build/save")
    public String saveEntity(
            @RequestParam("description") final String description,
            @RequestParam("address") final String address,
            @RequestParam("accessCode") final String accessCode,
            @RequestParam("ownerId") final Long ownerId) {

        final var owner = Build.builder()
                .description(description)
                .address(address)
                .accessCode(accessCode)
                .owner(ownerService.findById(ownerId))
                .build();

        service.save(owner);

        return "redirect:/build";
    }

    @GetMapping("/build/edit/{id}")
    public String editEntity(@PathVariable final Long id, final Model model) {
        final var entity = service.findById(id);
        model.addAttribute("build", entity);
        model.addAttribute("owners", ownerService.findAll());

        return "pages/build/add-build";
    }

    @GetMapping("/build/delete/{id}")
    public String deleteEntity(@PathVariable final Long id) {
        service.deleteById(id);

        return "redirect:/build";
    }

}
