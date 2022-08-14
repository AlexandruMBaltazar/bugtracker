package com.alex.authservice.security;

import com.alex.userservice.clients.appuser.AppUserClient;
import com.alex.userservice.clients.appuser.dto.AppUserDto;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserClient appUserClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUserDto appUserDto;

        try {
            appUserDto = appUserClient
                    .getAppUserByEmail(username)
                    .getBody();
            log.info("User found in the database: {}", username);
        } catch (FeignException.NotFound notFoundFeignException) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found");
        }

        Collection<SimpleGrantedAuthority> authorities = appUserDto
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toSet());

        return new User(appUserDto.getEmail(), appUserDto.getPassword(), authorities);
    }
}
