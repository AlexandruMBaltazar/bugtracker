package com.alex.userservice.clients.appuser;

import com.alex.userservice.clients.appuser.dto.response.AppUserAuthenticationResponseDto;
import com.alex.userservice.clients.appuser.dto.response.AppUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        value = "user-service",
        url = "${clients.user.url}"
)
public interface AppUserClient {

    @GetMapping(path = "api/v1/users/fetch-user/{email}")
    ResponseEntity<AppUserAuthenticationResponseDto> getAppUserByEmail(@PathVariable("email") String email);
}
