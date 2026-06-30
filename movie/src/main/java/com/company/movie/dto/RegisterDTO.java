package com.company.movie.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTO {
@NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    private String email;
    private String phone;
    private String address;
    @Size(min = 6, message = "minimun password lenght in 6 characters")
    private String password;
    private String confirmPassword;
    
}
