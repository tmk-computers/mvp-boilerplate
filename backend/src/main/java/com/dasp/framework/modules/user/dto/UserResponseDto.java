package com.dasp.framework.modules.user.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class UserResponseDto {

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Boolean isActive;
    private Set<String> roles;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
