package ru.mtsteta.courses.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorHandlersController {

    @GetMapping("/access_denied")
    public String getAccessDeniedForm() {
        return "access_denied";
    }
}
