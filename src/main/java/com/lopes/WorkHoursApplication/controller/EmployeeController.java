package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.Employee;
import com.lopes.WorkHoursApplication.service.EmployeeService;
import com.lopes.WorkHoursApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeService service;
    private final UserService userService;

    @GetMapping("/employee")
    public String getEmployees(Model model) {
        model.addAttribute("employees", service.findAll());
        return "pages/employee/employee";
    }

    @GetMapping("/employee/add")
    public String addEmployee(Model model) {
        model.addAttribute("users", userService.findAll());
        return "pages/employee/add-employee";
    }

    @PostMapping("/employee/save")
    public String save(
            @RequestParam("name") String name,
            @RequestParam("nickName") String nickName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam(value = "userId", required = false) Long userId) {

        Employee employee = Employee.builder()
                .name(name)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();

        if (Objects.nonNull(userId)) {
            employee.setUser(userService.findById(userId));
        }

        service.save(employee);

        return "redirect:/employee";
    }

}
