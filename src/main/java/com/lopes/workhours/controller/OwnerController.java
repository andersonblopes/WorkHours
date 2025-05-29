package com.lopes.workhours.controller;

import com.lopes.workhours.domain.entities.Owner;
import com.lopes.workhours.domain.entities.User;
import com.lopes.workhours.domain.filter.OwnerFilter;
import com.lopes.workhours.service.OwnerService;
import com.lopes.workhours.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String getEmployees(final @ModelAttribute OwnerFilter filter,
                               final Pageable pageable,
                               final Model model) {
        model.addAttribute("owners", service.findByFilter(filter, pageable));
        model.addAttribute("filter", filter);
        
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
            @RequestParam(value = "id", required = false) final Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "nickName", required = false) String nickName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam(value = "userId", required = false) Long userId) {

        final var owner = (id != null) ? service.findById(id) : new Owner();

        owner.setName(name);
        owner.setNickName(nickName);
        owner.setPhoneNumber(phoneNumber);
        owner.setEmail(email);
        owner.setUser(userService.findById(userId));

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
