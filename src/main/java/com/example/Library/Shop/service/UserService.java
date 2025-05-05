package com.example.Library.Shop.service;

import com.example.Library.Shop.model.User;
import com.example.Library.Shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }

        if (user.getPassword() == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }

        if (!user.getPassword().matches("^(?=.*[A-Z])(?=.*\\d).{6,12}$")) {
            throw new IllegalArgumentException("Parola trebuie sa contina cel putin o majuscula si o cifra");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    public void deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }


    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(email)
                .flatMap(userRepository::findByEmail);
    }


    public User getLoggedInUser() {
        return getCurrentAuthenticatedUser();
    }


    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            String email = authentication.getName();
            return userRepository.findByEmail(email).orElse(null);
        }

        return null;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
