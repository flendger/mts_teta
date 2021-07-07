package ru.mtsteta.courses.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.service.CourseLister;
import ru.mtsteta.courses.service.StatisticsCounter;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseLister courseLister;
    @Autowired
    private StatisticsCounter statisticsCounter;

    @GetMapping("/interesting")
    public List<Course> getInterestingCourses() {
        statisticsCounter.countHandlerCall();
        // У нас есть бизнес инсайт, что все интересные курсы написал Вася
        return courseLister.coursesByAuthor("Вася");
    }
}
