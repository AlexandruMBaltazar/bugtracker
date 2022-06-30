package com.alex.user.dto.response;

import com.alex.user.role.dto.response.RoleDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;
@Data
public class AppUserDto implements Serializable {
    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Email
    private String email;

    private Set<RoleDto> roles;

}
