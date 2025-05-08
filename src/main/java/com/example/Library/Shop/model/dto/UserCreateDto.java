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
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._-]{3,19}$", message = "Username-ul trebuie să înceapă cu o literă și să aibă între 4 și 20 de caractere. Sunt permise doar litere, cifre, puncte, _ și -.")
    private String username;


    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).{6,12}$", message = "Parola trebuie sa contina cel putin 6 cifre dintre care minim o majuscula si o cifra")
    private String password;


    private String confirmPassword;

    @NotBlank
    @Pattern(regexp = "^[A-ZȘȚĂÎÂ][a-zșțăîâ]+( [A-ZȘȚĂÎÂ][a-zșțăîâ]+)*$", message = "Numele trebuie să înceapă cu literă mare și să conțină doar litere și spații.")
    private String name;


    @Email
    @NotBlank
    private String email;


    @NotBlank
    @Pattern(
            regexp = "^[A-ZȘȚĂÎÂ][a-zșțîăâ]+(?: [A-ZȘȚĂÎÂ][a-zșțîăâ]+)*$",
            message = "Numele țării trebuie să înceapă cu literă mare și să conțină doar litere și spații"
    )
    private String country;



    @NotBlank
    @Pattern(
            regexp = "^[A-ZȘȚĂÎÂ][a-zșțîăâ]+(?:[- ][A-ZȘȚĂÎÂ][a-zșțîăâ]+)*$",
            message = "Numele orașului trebuie să înceapă cu literă mare și poate conține doar litere, spații sau cratime"
    )
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

