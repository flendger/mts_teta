package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mtsteta.courses.dao.UserRepository;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.Role;
import ru.mtsteta.courses.domain.User;
import ru.mtsteta.courses.dto.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public List<UserDto> findAllByCoursesNotContains(Course course) {
        return userRepository.findAllByCoursesNotContains(course)
                .stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findById(Long id) {
        return userRepository.findById(id).map(UserDto::from);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void save(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(userDto.getRoles());
        userRepository.save(user);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public Optional<UserDto> findUserDtoByUsername(String username) {
        return userRepository.findUserByUsername(username)
                .map(UserDto::from);
    }

    public boolean exists(String username) {
        return userRepository.findUserByUsername(username).isPresent();
    }

    public UserDto createUser(String username) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("default"); //потом можно заменить на дефолтный сгенерированный
        Role role = roleService.getDefaultRole();
        user.setRoles(Set.of(role));
        userRepository.save(user);
        return UserDto.from(user);
    }
}
