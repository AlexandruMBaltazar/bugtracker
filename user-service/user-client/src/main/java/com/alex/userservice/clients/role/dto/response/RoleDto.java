package com.alex.userservice.clients.role.dto.response;

import com.alex.userservice.clients.role.dto.ERole;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDto implements Serializable {
    private Long id;
    private ERole name;
}
