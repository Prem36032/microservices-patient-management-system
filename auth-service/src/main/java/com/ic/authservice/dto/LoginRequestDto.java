package com.ic.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Email(message = "Invalid Email received")
    @NotNull(message = "Email can't be null")
    private String email;

    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password can't be empty")
    @Min(value = 8, message = "Minimum password Length is 8 character")
    private String password;
}
