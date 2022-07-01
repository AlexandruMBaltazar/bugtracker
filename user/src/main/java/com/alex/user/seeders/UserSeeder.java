package com.alex.user.seeders;

import com.alex.user.AppUser;
import com.alex.user.AppUserRepository;
import com.alex.user.role.ERole;
import com.alex.user.role.Role;
import com.alex.user.role.RoleRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Slf4j
@Component
@Order(2)
public class UserSeeder implements CommandLineRunner {
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private RoleRepository roleRepository;
    private final Faker faker = new Faker();

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }

    private void loadUserData() {
        if (appUserRepository.count() == 0) {

            roleRepository.findAll().forEach(role -> {
                AppUser appUser = AppUser.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .password(BCrypt.hashpw("password", BCrypt.gensalt()))
                        .roles(Set.of(role))
                        .build();

                appUserRepository.save(appUser);
            });
        }
    }
}
