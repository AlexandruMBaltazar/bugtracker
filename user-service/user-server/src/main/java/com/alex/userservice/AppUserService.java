package com.alex.userservice;
import com.alex.userservice.clients.appuser.dto.AppUserDto;
import com.alex.userservice.clients.role.dto.ERole;
import com.alex.userservice.exception.UserGlobalErrorCode;
import com.alex.userservice.exception.UserGlobalRequestException;
import com.alex.userservice.role.Role;
import com.alex.userservice.role.RoleRepository;
import com.alex.userservice.utility.MapperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public record AppUserService(
        AppUserRepository appUserRepository,
        RoleRepository roleRepository,
        PasswordEncoder encoder
) {

    public AppUserDto createUser(AppUserDto appUserDto) {
        appUserDto.setPassword(encoder.encode(appUserDto.getPassword()));

        AppUser appUser =  MapperUtility.map(appUserDto, AppUser.class);
        Role role = roleRepository.findByName(ERole.DEVELOPER);
        appUser.setRoles(Set.of(role));

        appUser =  appUserRepository.save(appUser);

        return MapperUtility.map(appUser, AppUserDto.class);
    }

    public AppUserDto getAppUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserGlobalRequestException(
                        UserGlobalErrorCode.ERROR_ENTITY_NOT_FOUND,
                        HttpStatus.BAD_REQUEST
                ));

        return MapperUtility.map(appUser, AppUserDto.class);
    }
}
