package com.lopes.WorkHoursApplication.controller;

import com.lopes.WorkHoursApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/users/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/users/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String confirmPassword) {

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            return "redirect:/users/register?error=passwordsDoNotMatch"; // Redirect with error
        }

        userService.createUser(username, password);

        return "redirect:/login";
    }
}