package com.example.Library.Shop.model.dto;

import com.example.Library.Shop.model.Users;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserCreateDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._]{3,19}$", message = "Username-ul trebuie să înceapă cu o literă și poate conține litere, cifre, puncte și underscore. Lungime între 4 și 20 de caractere.")
    private String username;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Parola trebuie să aibă minim 8 caractere, inclusiv o literă mare, o literă mică, o cifră și un caracter special."
    )
    private String password;
    private String confirmPassword;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Campul trebuie completat")
    private String name;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String address;
    private String role = "ROLE_USER";

    public Users mapToUser() {
        Users user = new Users();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setRole(this.role);
        user.setEmail(this.email);
        user.setAddress(this.address);
        return user;
    }
}

