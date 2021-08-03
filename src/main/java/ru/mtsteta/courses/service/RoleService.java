package ru.mtsteta.courses.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mtsteta.courses.dao.RoleRepository;
import ru.mtsteta.courses.domain.Role;
import ru.mtsteta.courses.exceptions.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final static String DEFAULT_ROLE = "ROLE_STUDENT";

    private final RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role getDefaultRole() {
        return roleRepository.findByName(DEFAULT_ROLE).orElseThrow(() -> new NotFoundException("Default role doesn't exist"));
    }
}
