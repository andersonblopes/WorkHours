package com.lopes.workhours.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get the error status code
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Return the appropriate view based on the status code
            if (statusCode == 404) {
                return "error/404";  // 404 error page
            } else if (statusCode == 500) {
                return "error/500";  // 500 error page
            }
        }

        // Default error page
        model.addAttribute("error", "Something went wrong. Please try again later.");
        return "error/500";  // General error page
    }
}


