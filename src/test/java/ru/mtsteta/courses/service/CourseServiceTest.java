package ru.mtsteta.courses.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.mtsteta.courses.dao.UserRepository;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.User;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void assignUserToCourse() {
        courseService.assignUserToCourse(7L, 2L);

        Optional<User> optionalUser = userRepository.findById(2L);
        assertTrue(optionalUser.isPresent());

        User user = optionalUser.get();
        Set<Course> courses = user.getCourses();
        assertNotNull(courses);
        assertEquals(2, courses.size());
        assertTrue(courses.stream().anyMatch(course -> course.getId().equals(7L)));
    }

    @Test
    @Transactional
    void dismissUserFromCourse() {
        courseService.dismissUserFromCourse(8L, 2L);

        Optional<User> optionalUser = userRepository.findById(2L);
        assertTrue(optionalUser.isPresent());

        User user = optionalUser.get();
        Set<Course> courses = user.getCourses();
        assertNotNull(courses);
        assertTrue(courses.isEmpty());
    }
}