package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.Owner;
import com.lopes.WorkHoursApplication.domain.entities.User;
import com.lopes.WorkHoursApplication.service.OwnerService;
import com.lopes.WorkHoursApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class OwnerController {

    private final OwnerService service;
    private final UserService userService;

    @GetMapping("/owner")
    public String getEmployees(Model model) {
        model.addAttribute("owners", service.findAll());
        return "pages/owner/owner";
    }

    @GetMapping("/owner/add")
    public String add(Model model) {

        List<User> owners = userService.findAll();
        model.addAttribute("users", owners);

        return "pages/owner/add-owner";
    }

    @PostMapping("/owner/save")
    public String save(
            @RequestParam("name") String name,
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam(value = "userId", required = false) Long userId) {

        Owner owner = Owner.builder()
                .name(name)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .email(email)
                .user(userService.findById(userId))
                .build();

        service.save(owner);

        return "redirect:/owner";
    }

    @GetMapping("/owner/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Owner owner = service.findById(id);
        model.addAttribute("owner", owner);
        model.addAttribute("users", userService.findAll());
        return "pages/owner/add-owner";
    }

    @GetMapping("/owner/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/owner";
    }

}
