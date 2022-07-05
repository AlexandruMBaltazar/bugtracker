package com.alex.userservice;
import com.alex.userservice.clients.appuser.dto.response.AppUserAuthenticationResponseDto;
import com.alex.userservice.clients.appuser.dto.response.AppUserResponseDto;
import com.alex.userservice.exception.UserGlobalErrorCode;
import com.alex.userservice.exception.UserGlobalRequestException;
import com.alex.userservice.utility.MapperUtility;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public record AppUserService(AppUserRepository appUserRepository) {
    public AppUserAuthenticationResponseDto getAppUserByEmail(String email) {
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UserGlobalRequestException(
                        UserGlobalErrorCode.ERROR_ENTITY_NOT_FOUND,
                        HttpStatus.BAD_REQUEST
                ));

        return MapperUtility.map(appUser, AppUserAuthenticationResponseDto.class);
    }
}
