package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtsteta.courses.dao.CourseRepository;
import ru.mtsteta.courses.domain.Course;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public List<Course> coursesByAuthor(String author) {
        return courseRepository.findByAuthorLike(author);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public List<Course> findByTitlePrefix(String titlePrefix) {
        return courseRepository.findByTitleLike(titlePrefix);
    }

    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void delete(Long id) {
//        courseRepository.delete(id);
    }
}
