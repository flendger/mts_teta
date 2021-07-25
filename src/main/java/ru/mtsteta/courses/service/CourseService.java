package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mtsteta.courses.dao.CourseRepository;
import ru.mtsteta.courses.dao.UserRepository;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.User;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

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
        courseRepository.deleteById(id);
    }

    @Transactional
    public void assignUserToCourse(Long courseId, Long userId) {
        Course course = courseRepository.getById(courseId);

        User user = userRepository.getById(userId);
        course.getUsers().add(user);

        courseRepository.save(course);
    }

    @Transactional
    public void dismissUserFromCourse(Long courseId, Long userId) {
        Course course = courseRepository.getById(courseId);

        User user = userRepository.getById(userId);
        course.getUsers().remove(user);

        courseRepository.save(course);
    }
}
