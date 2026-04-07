package com.app.userservice.dto.response;

import com.app.userservice.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for exposing user info to other services (Admin Service).
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

    public static UserDTO fromEntity(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .provider(user.getProvider().name())
                .imageUrl(user.getImageUrl())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
