package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtsteta.courses.dao.UserRepository;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllByCoursesNotContains(Course course) {
        return userRepository.findAllByCoursesNotContains(course);
    }
}
