package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.mtsteta.courses.domain.Lesson;
import ru.mtsteta.courses.dto.LessonDto;
import ru.mtsteta.courses.exceptions.NotFoundException;
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

    @GetMapping("/{id}")
    public String editLesson(Model model, @PathVariable("id") Long id) {
        Lesson lesson = lessonService.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Lesson [%d] not found", id)));
        model.addAttribute("lessonDto", LessonDto.from(lesson));
        return "lesson_form";
    }

    @PostMapping
    public String saveLesson(@Valid LessonDto lessonDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        }

        lessonService.save(lessonDto);
        return "redirect:/course/" + lessonDto.getCourseId();
    }

    @DeleteMapping("/{course_id}/{lesson_id}")
    public String deleteLesson(@PathVariable("course_id") Long courseId, @PathVariable("lesson_id") Long lessonId) {
        lessonService.deleteById(lessonId);
        return "redirect:/course/" + courseId;
    }
}
