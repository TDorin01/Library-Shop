package com.example.Library.Shop.repository;

import com.example.Library.Shop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users>findByUsername(String username);

    Optional<Users> findByEmail(String email);
}
