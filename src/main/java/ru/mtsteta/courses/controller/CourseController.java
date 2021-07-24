package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.dto.LessonDto;
import ru.mtsteta.courses.exceptions.NotFoundException;
import ru.mtsteta.courses.service.CourseService;
import ru.mtsteta.courses.service.StatisticsCounter;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final StatisticsCounter statisticsCounter;

    @GetMapping("/interesting")
    @ResponseBody
    public List<Course> getInterestingCourses() {
        statisticsCounter.countHandlerCall();
        // У нас есть бизнес инсайт, что все интересные курсы написал Вася
        return courseService.coursesByAuthor("Вася");
    }

    @GetMapping
    public String courseTable(Model model, @RequestParam(name = "titlePrefix", required = false) String titlePrefix) {
        model.addAttribute("courses", courseService.findByTitlePrefix((titlePrefix == null ? "" : titlePrefix) + "%")
                .stream()
                .sorted(Comparator.comparing(Course::getId))
                .collect(Collectors.toList()));
        model.addAttribute("activePage", "courses");
        return "course_list";
    }

    @GetMapping("/{id}")
    @Transactional
    public String editCourse(Model model, @PathVariable("id") Long id) {
        Course course = courseService.findById(id).orElseThrow(() -> new NotFoundException(String.format("Course [%d] not found", id)));
        model.addAttribute("course", course);
        model.addAttribute("lessons", course.getLessons()
                .stream()
                .map(LessonDto::from)
                .sorted(Comparator.comparing(LessonDto::getId))
                .collect(Collectors.toList()));
        return "course_form";
    }

    @PostMapping
    public String saveCourse(@Valid Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course_form";
        }

        courseService.save(course);
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String createCourse(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }

    @DeleteMapping("/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return "redirect:/course";
    }

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException ex, Model model) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        model.addAttribute("msg", ex.getMessage());
        return modelAndView;
    }
}
