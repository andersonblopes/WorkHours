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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/build")
public class BuildController {

    private final BuildService service;
    private final OwnerService ownerService;

    @GetMapping()
    public String getAllEntities(final Model model) {
        model.addAttribute("builds", service.findAll());

        return "pages/build/build";
    }

    @GetMapping("/add")
    public String addEntity(final Model model) {
        model.addAttribute("owners", ownerService.findAll());

        return "pages/build/add-build";
    }

    @PostMapping("/save")
    public String saveEntity(
            @RequestParam(value = "id", required = false) final Long id,
            @RequestParam("description") final String description,
            @RequestParam("address") final String address,
            @RequestParam("accessCode") final String accessCode,
            @RequestParam("ownerId") final Long ownerId) {

        final var build = (id != null) ? service.findById(id) : new Build();

        build.setDescription(description);
        build.setAddress(address);
        build.setAccessCode(accessCode);
        build.setOwner(ownerService.findById(ownerId));

        service.save(build);

        return "redirect:/build";
    }

    @GetMapping("/edit/{id}")
    public String editEntity(@PathVariable final Long id, final Model model) {
        final var entity = service.findById(id);
        model.addAttribute("build", entity);
        model.addAttribute("owners", ownerService.findAll());

        return "pages/build/add-build";
    }

    @GetMapping("/delete/{id}")
    public String deleteEntity(@PathVariable final Long id) {
        service.deleteById(id);

        return "redirect:/build";
    }

}
