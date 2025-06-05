package com.example.Library.Shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(unique = true)
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Parola nu poate fi goală")
    private String password;

    @Column
    @NotBlank(message = "Numele nu poate fi gol")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Campul trebuie completat")
    private String name;

    @Column(unique = true)
    @Email(message = "Email invalid")
    @NotBlank(message = "Emailul nu poate fi gol")
    private String email;

    @NotBlank(message = "Tara nu poate fi goala")
    private String country;

    @NotBlank(message = "Orasul nu poate fi gol")
    private String city;

    @NotBlank(message = "Adresa nu poate fi goala")
    private String address;

    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Book> bookList;
}

