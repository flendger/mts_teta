package ru.mtsteta.courses.dto;

import lombok.Data;
import ru.mtsteta.courses.domain.Course;
import ru.mtsteta.courses.domain.Role;
import ru.mtsteta.courses.domain.User;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UserDto {
    private Long id;

    @NotBlank(message = "Имя пользователя должно быть заполнено")
    private String username;

    @NotBlank(message = "Пароль должен быть заполнен")
    private String password;

    private Set<Course> courses;
    private Set<Role> roles;

    public static UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setPassword("");
        userDto.setCourses(user.getCourses());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto user = (UserDto) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
