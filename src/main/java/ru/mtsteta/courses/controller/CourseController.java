package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.service.CourseService;
import ru.mtsteta.courses.service.StatisticsCounter;

import java.util.List;

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
    public String courseForm(Model model, @PathVariable("id") Long id) {
        model.addAttribute("course", courseService.findById(id));
        return "course_form";
    }
}
