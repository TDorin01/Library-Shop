package com.example.Library.Shop.model.dto;
import com.example.Library.Shop.model.User;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserCreateDto {
    @NotBlank
    private String username;

    @Size(min = 8, max = 50, message = "Parola incorecta: Minim 8 caractere necesare")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{8,50}$", message = "Parola trebuie sa contina cel putin o majuscula si o cifra")
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

    public User mapToUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setRole(this.role);
        user.setEmail(this.email);
        user.setAddress(this.address);
        return user;
    }
}

