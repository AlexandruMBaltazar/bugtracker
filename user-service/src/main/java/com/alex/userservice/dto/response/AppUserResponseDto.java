package com.alex.userservice.dto.response;

import com.alex.userservice.role.dto.response.RoleDto;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;
@Data
public class AppUserResponseDto implements Serializable {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;

    private Set<RoleDto> roles;

}
