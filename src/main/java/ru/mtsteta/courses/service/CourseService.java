package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtsteta.courses.dao.CourseRepository;
import ru.mtsteta.courses.domain.Course;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> coursesByAuthor(String name) {
        List<Course> allCourses = courseRepository.findAll();
        return allCourses.stream().filter(course -> course.getAuthor().equals(name)).collect(Collectors.toList());
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.delete(id);
    }
}
