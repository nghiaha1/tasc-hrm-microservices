package com.tasc.project.user.seeder;

import com.tasc.entity.BaseStatus;
import com.tasc.project.user.entity.Role;
import com.tasc.project.user.entity.User;
import com.tasc.project.user.repository.RoleRepository;
import com.tasc.project.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ApplicationSeeder implements CommandLineRunner {

    private boolean isSeeding = false;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private void generateRole() {
        List<Role> roles = new ArrayList<>();

        Optional<Role> checkEmployeeRole = roleRepository.findRoleByName("ROLE_EMPLOYEE");

        if (checkEmployeeRole.isEmpty()) {
            Role employeeRole = Role.builder()
                    .name("ROLE_EMPLOYEE")
                    .description("Employee role")
                    .build();
            employeeRole.setStatus(BaseStatus.ACTIVE);
            roles.add(employeeRole);
        }

        Optional<Role> checkAccountantRole = roleRepository.findRoleByName("ROLE_ACCOUNTANT");

        if (checkAccountantRole.isEmpty()) {
            Role accountantRole = Role.builder()
                    .name("ROLE_ACCOUNTANT")
                    .description("Accountant role")
                    .build();
            accountantRole.setStatus(BaseStatus.ACTIVE);
            roles.add(accountantRole);
        }

        Optional<Role> checkDirectorRole = roleRepository.findRoleByName("ROLE_DIRECTOR");

        if (checkDirectorRole.isEmpty()) {
            Role directorRole = Role.builder()
                    .name("ROLE_DIRECTOR")
                    .description("Director role")
                    .build();
            directorRole.setStatus(BaseStatus.ACTIVE);
            roles.add(directorRole);
        }

        roleRepository.saveAll(roles);

    }

    private void generateUser() {
        Optional<Role> optionalEmployeeRole = roleRepository.findRoleByName("ROLE_EMPLOYEE");
        Role employeeRole = optionalEmployeeRole.get();

        List<User> users = new ArrayList<>();

        Optional<User> optionalEmployee = userRepository.findUserByUsername("employee_1");

        if (optionalEmployee.isEmpty()) {
            User employee = User.builder()
                    .username("employee_1")
                    .password(bCryptPasswordEncoder.encode("123"))
                    .role(employeeRole)
                    .build();
            employee.setStatus(BaseStatus.ACTIVE);
            users.add(employee);
        }

        Optional<Role> optionalAccountantRole = roleRepository.findRoleByName("ROLE_ACCOUNTANT");
        Role accountantRole = optionalAccountantRole.get();

        Optional<User> optionalAccountant = userRepository.findUserByUsername("accountant_1");

        if (optionalAccountant.isEmpty()) {
            User accountant = User.builder()
                    .username("accountant_1")
                    .password(bCryptPasswordEncoder.encode("123"))
                    .role(accountantRole)
                    .build();
            accountant.setStatus(BaseStatus.ACTIVE);
            users.add(accountant);
        }

        Optional<Role> optionalDirectorRole = roleRepository.findRoleByName("ROLE_DIRECTOR");
        Role directorRole = optionalDirectorRole.get();

        Optional<User> optionalDirector = userRepository.findUserByUsername("director_1");

        if (optionalDirector.isEmpty()) {
            User director = User.builder()
                    .username("director_1")
                    .password(bCryptPasswordEncoder.encode("123"))
                    .role(directorRole)
                    .build();
            director.setStatus(BaseStatus.ACTIVE);
            users.add(director);
        }

        userRepository.saveAll(users);
    }

    @Override
    public void run(String... args) {
        if (isSeeding) {
            generateRole();
            generateUser();
        }
    }
}
