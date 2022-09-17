package com.alex.userservice.clients.appuser.dto.response;

import com.alex.userservice.clients.role.dto.response.RoleDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Data
@Getter
@Setter
public class AppUserResponseDto implements Serializable {
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected Set<RoleDto> roles;
}
