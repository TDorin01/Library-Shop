package com.example.Library.Shop.repository;

import com.example.Library.Shop.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users>findByUsername(String username);

}
