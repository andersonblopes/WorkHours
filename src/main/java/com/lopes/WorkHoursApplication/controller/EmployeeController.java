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
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam("name") String name,
            @RequestParam("nickName") String nickName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("email") String email,
            @RequestParam(value = "userId", required = false) Long userId) {

        Employee employee = (id != null) ? service.findById(id) : new Employee();

        employee.setName(name);
        employee.setNickName(nickName);
        employee.setPhoneNumber(phoneNumber);
        employee.setEmail(email);

        if (Objects.nonNull(userId)) {
            employee.setUser(userService.findById(userId));
        }

        service.save(employee);
        return "redirect:/employee";
    }

    @GetMapping("/employee/edit/{id}")
    public String editEmployee(@PathVariable Long id, Model model) {
        Employee employee = service.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("users", userService.findAll());
        return "pages/employee/add-employee";
    }

    @GetMapping("/employee/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        service.deleteById(id);
        return "redirect:/employee";
    }
}
