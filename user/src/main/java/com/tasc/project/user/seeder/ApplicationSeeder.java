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

    private boolean isSeeding = true;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    private void generateRole() {
        List<Role> roles = new ArrayList<>();

        Optional<Role> optionalRole1 = roleRepository.findRoleByName("ROLE_USER");

        if (optionalRole1.isEmpty()) {
            Role userRole = Role.builder()
                    .name("ROLE_USER")
                    .description("User role")
                    .build();
            userRole.setStatus(BaseStatus.ACTIVE);
            roles.add(userRole);
        }

        Optional<Role> optionalRole2 = roleRepository.findRoleByName("ROLE_ADMIN");

        if (optionalRole2.isEmpty()) {
            Role adminRole = Role.builder()
                    .name("ROLE_ADMIN")
                    .description("Admin role")
                    .build();
            adminRole.setStatus(BaseStatus.ACTIVE);
            roles.add(adminRole);
        }

        roleRepository.saveAll(roles);

    }

    private void generateUser() {
        Optional<Role> optionalUserRole = roleRepository.findRoleByName("ROLE_USER");
        Role userRole = optionalUserRole.get();

        List<User> users = new ArrayList<>();

        Optional<User> optionalUser = userRepository.findUserByUsername("user1");

        if (optionalUser.isEmpty()) {
            User user = User.builder()
                    .username("user1")
                    .password(bCryptPasswordEncoder.encode("123"))
                    .role(userRole)
                    .build();
            user.setStatus(BaseStatus.ACTIVE);
            users.add(user);
        }

        Optional<Role> optionalAdminRole = roleRepository.findRoleByName("ROLE_ADMIN");
        Role adminRole = optionalAdminRole.get();

        Optional<User> optionalUser2 = userRepository.findUserByUsername("user1");

        if (optionalUser2.isEmpty()) {
            User admin = User.builder()
                    .username("admin1")
                    .password(bCryptPasswordEncoder.encode("123"))
                    .role(adminRole)
                    .build();
            admin.setStatus(BaseStatus.ACTIVE);
            users.add(admin);
        }

        userRepository.saveAll(users);
    }

    @Override
    public void run(String... args) throws Exception {
        if (isSeeding) {
            generateRole();
            generateUser();
        }
    }
}
