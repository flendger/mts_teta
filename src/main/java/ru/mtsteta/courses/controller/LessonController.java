package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtsteta.courses.dto.LessonDto;
import ru.mtsteta.courses.service.LessonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/new")
    public String newLesson(Model model, @RequestParam(name = "course_id") Long courseId) {
        model.addAttribute("lessonDto", new LessonDto(courseId));
        return "lesson_form";
    }

    @PostMapping
    public String saveLesson(@Valid LessonDto lessonDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        }

        lessonService.save(lessonDto);
        return "redirect:course/" + lessonDto.getCourseId();
    }
}
