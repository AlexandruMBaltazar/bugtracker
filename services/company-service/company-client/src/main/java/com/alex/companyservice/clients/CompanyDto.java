package com.alex.companyservice.clients;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link com.alex.companyservice.Company} entity
 */
@Data
@Getter
@Setter
public class CompanyDto implements Serializable {
    private Long id;
    @NotBlank(message = "Company name is required")
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}