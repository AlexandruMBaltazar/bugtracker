package com.alex.userservice.clients.appuser.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AppUserAuthResponseDto extends AppUserResponseDto {
    private String password;
}
