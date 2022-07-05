package com.alex.userservice.clients.appuser.dto.response;

import com.alex.userservice.clients.role.dto.response.RoleDto;
import lombok.Data;
import java.io.Serializable;
import java.util.Set;

@Data
public class AppUserResponseDto implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleDto> roles;

}
