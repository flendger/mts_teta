package ru.mtsteta.courses.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.mtsteta.courses.dao.RoleRepository;
import ru.mtsteta.courses.domain.Role;
import ru.mtsteta.courses.exceptions.NotFoundException;

import java.util.Optional;

class RoleServiceTest {

    @Test
    void getDefaultRole() {
        RoleRepository roleRepository = Mockito.mock(RoleRepository.class);
        RoleService roleService = new RoleService(roleRepository);

        Mockito.when(roleRepository.findByName("ROLE_STUDENT"))
                .thenReturn(Optional.empty());


        Assertions.assertThrows(NotFoundException.class, roleService::getDefaultRole);

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_STUDENT");
        Mockito.when(roleRepository.findByName("ROLE_STUDENT"))
                .thenReturn(Optional.of(role));

        Assertions.assertDoesNotThrow(roleService::getDefaultRole);
        Role defaultRole = roleService.getDefaultRole();
        Assertions.assertEquals("ROLE_STUDENT", defaultRole.getName());
    }
}