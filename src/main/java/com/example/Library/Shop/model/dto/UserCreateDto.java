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

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,12}$", message = "Parola trebuie sa contina cel putin 6 cifre dintre care minim o majuscula si o cifra")
    private String password;


    private String confirmPassword;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Campul trebuie completat")
    private String name;

    @Email
    @NotBlank
    private String email;


    @NotBlank
    private String country;


    @NotBlank
    private String city;

    @NotBlank
    private String address;
    private String role = "ROLE_USER";

    public User mapToUser() {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setName(this.name);
        user.setRole(this.role);
        user.setCountry(this.country);
        user.setCity(this.city);
        user.setEmail(this.email);
        user.setAddress(this.address);
        return user;
    }
}

