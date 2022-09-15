package com.alex.userservice;
import com.alex.userservice.clients.appuser.dto.request.AppUserRequestDto;
import com.alex.userservice.clients.appuser.dto.response.AppUserAuthResponseDto;
import com.alex.userservice.clients.appuser.dto.response.AppUserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public record AppUserController(AppUserService appUserService) {

    @PostMapping
    public ResponseEntity<AppUserResponseDto> createUser(@Valid @RequestBody AppUserRequestDto appUserRequestDto) {
        AppUserResponseDto appUserResponseDto = appUserService.createUser(appUserRequestDto);
        return new ResponseEntity<>(appUserResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/fetch-user/{email}")
    public ResponseEntity<AppUserAuthResponseDto> getAppUserByEmail(@PathVariable String email) {
        AppUserAuthResponseDto appUserAuthResponseDtoDto = appUserService.getAppUserByEmail(email);
        return new ResponseEntity<>(appUserAuthResponseDtoDto, HttpStatus.OK);
    }
}
