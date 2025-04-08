package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.domain.entities.Employee;
import com.lopes.WorkHoursApplication.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/employee")
    public String getEmployees(Model model) {
        model.addAttribute("employees", service.getAllLogs());
        return "employee";
    }

    @GetMapping("/employee/add")
    public String addEmployee() {
        return "add-employee";
    }

    @PostMapping("/employee/save")
    public String save(
            @RequestParam("name") String name,
            @RequestParam("nickName") String nickName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email) {

        Employee employee = Employee.builder()
                .name(name)
                .nickName(nickName)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();

        service.save(employee);

        return "redirect:/employee";
    }

}
