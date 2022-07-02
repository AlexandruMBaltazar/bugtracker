package com.alex.userservice.seeders;

import com.alex.userservice.AppUser;
import com.alex.userservice.AppUserRepository;
import com.alex.userservice.role.RoleRepository;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

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
