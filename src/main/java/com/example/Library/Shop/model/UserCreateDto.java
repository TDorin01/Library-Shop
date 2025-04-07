package com.example.Library.Shop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDto {
    private String username;
    private String password;
    private String verifyPassword;
    private String email;
    private String address;
    private String role = "USER";

    public Users mapToUser(){
        Users user = new Users();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setRole(this.role);
        user.setEmail(this.email);
        user.setAddress(this.address);
        return user;
    }
}

