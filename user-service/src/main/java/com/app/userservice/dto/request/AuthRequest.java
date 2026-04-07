package com.app.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid address")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
