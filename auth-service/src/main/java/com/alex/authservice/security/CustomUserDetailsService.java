package com.alex.authservice.security;

import com.alex.userservice.clients.appuser.AppUserClient;
import com.alex.userservice.clients.appuser.dto.response.AppUserAuthenticationResponseDto;
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
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private AppUserClient appUserClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUserAuthenticationResponseDto appUserDto;

        log.info("APP USER REQUEST MADE");

        try {
            appUserDto = appUserClient
                    .getAppUserByEmail(username)
                    .getBody();
        } catch (FeignException exception) {
            throw new UsernameNotFoundException("User not found");
        }

        Collection<SimpleGrantedAuthority> grantedAuthorities = appUserDto
                .getRoles()
                .stream()
                .map(roleDto -> new SimpleGrantedAuthority(roleDto.getName().name()))
                .collect(Collectors.toSet());

        return new User(
                appUserDto.getEmail(),
                appUserDto.getPassword(),
                grantedAuthorities
        );

    }
}
