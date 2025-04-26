package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.Employee;
import com.lopes.WorkHoursApplication.service.EmployeeService;
import com.lopes.WorkHoursApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeService service;
    private final UserService userService;

    @GetMapping("/employee")
    public String getAllEntities(final Model model) {
        model.addAttribute("employees", service.findAll());
        
        return "pages/employee/employee";
    }

    @GetMapping("/employee/add")
    public String addEntity(final Model model) {
        model.addAttribute("users", userService.findAll());

        return "pages/employee/add-employee";
    }

    @PostMapping("/employee/save")
    public String saveEntity(
            @RequestParam(value = "id", required = false) final Long id,
            @RequestParam("name") final String name,
            @RequestParam("nickName") final String nickName,
            @RequestParam("phoneNumber") final String phoneNumber,
            @RequestParam("email") final String email,
            @RequestParam(value = "userId", required = false) final Long userId) {

        final var employee = (id != null) ? service.findById(id) : new Employee();

        employee.setName(name);
        employee.setNickName(nickName);
        employee.setPhoneNumber(phoneNumber);
        employee.setEmail(email);

        if (nonNull(userId)) {
            employee.setUser(userService.findById(userId));
        }

        service.save(employee);

        return "redirect:/employee";
    }

    @GetMapping("/employee/edit/{id}")
    public String editEntity(@PathVariable final Long id, final Model model) {
        final var employee = service.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("users", userService.findAll());

        return "pages/employee/add-employee";
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEntity(@PathVariable final Long id) {
        service.deleteById(id);

        return "redirect:/employee";
    }
}
