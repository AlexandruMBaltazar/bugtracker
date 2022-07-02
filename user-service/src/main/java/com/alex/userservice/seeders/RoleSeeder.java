package com.alex.userservice.seeders;

import com.alex.userservice.role.ERole;
import com.alex.userservice.role.Role;
import com.alex.userservice.role.RoleRepository;
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
