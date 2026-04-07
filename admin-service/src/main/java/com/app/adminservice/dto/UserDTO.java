package com.app.adminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Mirrors UserDTO from User Service.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String email;
    private String name;
    private String role;
    private String provider;
    private String imageUrl;
    private LocalDateTime createdAt;
}
