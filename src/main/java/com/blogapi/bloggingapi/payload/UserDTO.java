package com.blogapi.bloggingapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;

    @NotEmpty
    @Size(min = 4, message = "Name should have more than 4 characters")
    private String name;

    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "E-mail is invalid")
    private String email;

    @NotBlank
    @Size(min = 4, max = 16, message = "Password must have atleast 4 character and atmost 16 character")
    private String password;

    @NotBlank
    private String about;
}
