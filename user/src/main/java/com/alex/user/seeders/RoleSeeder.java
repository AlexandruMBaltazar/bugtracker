package com.alex.user.seeders;

import com.alex.user.role.ERole;
import com.alex.user.role.Role;
import com.alex.user.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Order(1)
public class RoleSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (roleRepository.count() == 0) {
            Arrays.stream(ERole.values()).forEach(
                    role -> roleRepository.save(new Role(role))
            );
        }
    }
}
