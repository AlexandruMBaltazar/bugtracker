package com.alex.user.role.dto.response;

import com.alex.user.role.ERole;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private Long id;
    private ERole name;
}
