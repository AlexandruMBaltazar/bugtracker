package com.alex.userservice.role.dto.response;

import com.alex.userservice.role.ERole;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private Long id;
    private ERole name;
}
