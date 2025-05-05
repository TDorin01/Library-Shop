package com.example.Library.Shop.service;

import com.example.Library.Shop.model.User;
import com.example.Library.Shop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clearDatabase() {
        userRepository.deleteAll();
    }

    private User buildValidUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("Test123A");
        user.setEmail(email);
        user.setName("Test User");
        user.setAddress("Strada Exemplu 123");
        user.setCity("Cluj");
        user.setCountry("Romania");
        return user;
    }

    @Test
    void testCreateUser_TwoInputs() {
        User user = buildValidUser("twoInputs", "test1@email.com");

        userService.createUser(user);

        User saved = userRepository.findByUsername("twoInputs").orElse(null);
        assertNotNull(saved);
        assertTrue(passwordEncoder.matches("Test123A", saved.getPassword()));
    }

    @Test
    void testCreateUser_DuplicateEmail() {
        User user1 = buildValidUser("user1", "duplicate@email.com");
        userService.createUser(user1);

        User user2 = buildValidUser("user2", "duplicate@email.com");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user2));
        assertEquals("Email already in use", ex.getMessage());
    }

    @Test
    void testCreateUser_NullPassword() {
        User user = buildValidUser("user1", "user@email.com");
        user.setPassword(null);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        assertEquals("Password cannot be null", ex.getMessage());
    }

    @Test
    void testCreateUser_NullUsername() {
        User user = buildValidUser(null, "user@email.com");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        assertEquals("Username cannot be null or blank", ex.getMessage());
    }

    @Test
    void testCreateUser_EmptyUsername() {
        User user = buildValidUser("   ", "user@email.com");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        assertEquals("Username cannot be null or blank", ex.getMessage());
    }

    @Test
    void testCreateUser_NullUser() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.createUser(null));
        assertEquals("User cannot be null", ex.getMessage());
    }
}
