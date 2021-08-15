package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.mtsteta.courses.exceptions.NotFoundException;
import ru.mtsteta.courses.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {
    private final UserService userService;

    @GetMapping
    public String getProfile(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userService.findUserDtoByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User [%s] not found", username))));
        return "user_profile";
    }

    @PostMapping("/avatar")
    public String updateAvatarImage(@RequestParam("avatar")MultipartFile avatar) {
        log.info("File name {}, file content type {}, file size {}",
                avatar.getOriginalFilename(),
                avatar.getContentType(),
                avatar.getSize());
        return "redirect:/profile";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        model.addAttribute("msg", ex.getMessage());
        return modelAndView;
    }
}
