package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mtsteta.courses.domain.Role;
import ru.mtsteta.courses.dto.UserDto;
import ru.mtsteta.courses.exceptions.NotFoundException;
import ru.mtsteta.courses.service.RoleService;
import ru.mtsteta.courses.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @ModelAttribute("roles")
    public List<Role> rolesAttribute() {
        return roleService.findAll();
    }

    @GetMapping
    public String findUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("activePage", "users");
        return "user_list";
    }

    @GetMapping("/{id}")
    public String getUser(Model model, @PathVariable("id") Long id) {
        UserDto userDto = userService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lesson [%d] not found", id)));

        model.addAttribute("user", userDto);
        return "user_form";
    }

    @GetMapping("/new")
    public String createUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @PostMapping
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_form";
        }

        userService.save(userDto);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user";
    }
}
