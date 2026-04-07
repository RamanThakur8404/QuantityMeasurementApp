package com.app.userservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequest {

    @NotBlank(message = "Password must not be blank")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[@#$%^&*()+\\-=])(?=.*\\d).{8,}$",
        message = "Password must be 8+ chars with 1 uppercase, 1 special char, 1 number"
    )
    private String password;
}
