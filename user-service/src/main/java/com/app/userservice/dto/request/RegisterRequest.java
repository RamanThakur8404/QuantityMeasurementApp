package com.app.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid address")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, max = 100, message = "Password must be 8-100 characters")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[@#$%^&*()+\\-=])(?=.*[0-9]).{8,}$",
        message = "Password must contain 1 uppercase, 1 special char, 1 number"
    )
    private String password;

    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;
}
