package com.alex.userservice;
import com.alex.userservice.clients.appuser.dto.response.AppUserAuthenticationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/users")
public record AppUserController(AppUserService appUserService) {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/fetch-user/{email}")
    public ResponseEntity<AppUserAuthenticationResponseDto> getAppUserByEmail(@PathVariable String email) {
        AppUserAuthenticationResponseDto appUserAuthenticationResponseDto = appUserService.getAppUserByEmail(email);
        return new ResponseEntity<>(appUserAuthenticationResponseDto, HttpStatus.OK);
    }
}
