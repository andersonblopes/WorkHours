package com.lopes.workhours.controller;

import com.lopes.workhours.domain.model.UserModel;
import com.lopes.workhours.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String registerUser(@ModelAttribute UserModel user) {

        // Check if passwords match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return "redirect:/users/register?error=passwordsDoNotMatch";
        }

        userService.createUser(user);

        return "redirect:/login";
    }
}
