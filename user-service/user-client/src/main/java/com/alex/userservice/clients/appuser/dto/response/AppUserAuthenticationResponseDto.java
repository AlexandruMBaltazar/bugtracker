package com.alex.userservice.clients.appuser.dto.response;

import com.alex.userservice.clients.role.dto.response.RoleDto;
import lombok.Data;
import java.io.Serializable;
import java.util.Set;

@Data
public class AppUserAuthenticationResponseDto implements Serializable {
    private Long id;
    private String email;
    private String password;
    private Set<RoleDto> roles;
}
