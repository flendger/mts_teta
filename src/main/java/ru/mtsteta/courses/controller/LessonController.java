package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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

    @Secured("ROLE_ADMIN")
    @PostMapping
    public String saveLesson(@Valid LessonDto lessonDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "lesson_form";
        }

        lessonService.save(lessonDto);
        return "redirect:/course/" + lessonDto.getCourseId();
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{lesson_id}")
    public String deleteLesson(@RequestParam("courseId") Long courseId, @PathVariable("lesson_id") Long lessonId) {
        lessonService.deleteById(lessonId);
        return "redirect:/course/" + courseId;
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        model.addAttribute("msg", ex.getMessage());
        return modelAndView;
    }
}
