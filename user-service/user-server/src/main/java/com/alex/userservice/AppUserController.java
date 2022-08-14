package com.alex.userservice;
import com.alex.userservice.clients.appuser.dto.AppUserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public record AppUserController(AppUserService appUserService) {

    @PostMapping
    public ResponseEntity<AppUserDto> createUser(@Valid @RequestBody AppUserDto appUserDto) {
        appUserDto = appUserService.createUser(appUserDto);
        return new ResponseEntity<>(appUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/fetch-user/{email}")
    public ResponseEntity<AppUserDto> getAppUserByEmail(@PathVariable String email) {
        AppUserDto appUserDto = appUserService.getAppUserByEmail(email);
        return new ResponseEntity<>(appUserDto, HttpStatus.OK);
    }
}
