package com.alex.user;

import com.alex.user.dto.response.AppUserDto;
import com.alex.user.exception.UserGlobalErrorCode;
import com.alex.user.exception.UserGlobalRequestException;
import com.alex.user.utility.MapperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public record AppUserService(AppUserRepository appUserRepository) {
    public AppUserDto getAppUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserGlobalRequestException(
                        UserGlobalErrorCode.ERROR_ENTITY_NOT_FOUND,
                        HttpStatus.BAD_REQUEST
                ));

        return MapperUtility.map(appUser, AppUserDto.class);
    }
}
