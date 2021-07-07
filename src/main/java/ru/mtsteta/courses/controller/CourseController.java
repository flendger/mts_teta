package ru.mtsteta.courses.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.service.CourseLister;
import ru.mtsteta.courses.service.StatisticsCounter;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseLister courseLister;
    private final StatisticsCounter statisticsCounter;

    @GetMapping("/interesting")
    public List<Course> getInterestingCourses() {
        statisticsCounter.countHandlerCall();
        // У нас есть бизнес инсайт, что все интересные курсы написал Вася
        return courseLister.coursesByAuthor("Вася");
    }
}
