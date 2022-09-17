package com.alex.userservice.clients.role.dto.response;

import com.alex.userservice.clients.role.dto.ERole;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Setter
@Getter
public class RoleDto implements Serializable {
    private Long id;
    private ERole name;
}
