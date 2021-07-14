package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.service.CourseService;
import ru.mtsteta.courses.service.StatisticsCounter;

import java.util.List;
import java.util.Optional;

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
    public String courseTable(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "course_list";
    }

    @GetMapping("/{id}")
    public String editCourse(Model model, @PathVariable("id") Long id) {
        Optional<Course> optionalCourse = courseService.findById(id);
        if (optionalCourse.isEmpty()) {
            return "course_not_found";
        }

        model.addAttribute("course", optionalCourse.get());
        return "course_form";
    }

    @PostMapping
    public String saveCourse(Course course) {
        courseService.save(course);
        return "redirect:/course";
    }

    @GetMapping("/new")
    public String createCourse(Model model) {
        model.addAttribute("course", new Course());
        return "course_form";
    }
}
