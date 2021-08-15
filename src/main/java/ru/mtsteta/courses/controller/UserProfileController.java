package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ru.mtsteta.courses.exceptions.NotFoundException;
import ru.mtsteta.courses.service.AvatarImageService;
import ru.mtsteta.courses.service.UserService;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
@Slf4j
public class UserProfileController {
    private final UserService userService;
    private final AvatarImageService avatarImageService;

    @GetMapping
    public String getProfile(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("user", userService.findUserDtoByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("User [%s] not found", username))));
        return "user_profile";
    }

    @PostMapping("/avatar")
    public String updateAvatarImage(@RequestParam("avatar")MultipartFile avatar, Principal principal) {
        log.info("File name {}, file content type {}, file size {}",
                avatar.getOriginalFilename(),
                avatar.getContentType(),
                avatar.getSize());

        try {
            avatarImageService.save(principal.getName(), avatar.getContentType(), avatar.getInputStream());
        } catch (IOException e) {
            log.info("", e);
        }

        return "redirect:/profile";
    }

    @GetMapping("/avatar")
    @ResponseBody
    public ResponseEntity<byte[]> getAvatarImage(Principal principal) {
        String username = principal.getName();
        String contentType = avatarImageService.getContentType(username).orElse(null);
        if (contentType == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] data = avatarImageService.getAvatarImageByUsername(username).orElse(new byte[0]);
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(data);
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        model.addAttribute("msg", ex.getMessage());
        return modelAndView;
    }
}
