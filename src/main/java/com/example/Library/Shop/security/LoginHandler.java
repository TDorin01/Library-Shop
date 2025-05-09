package com.example.Library.Shop.security;

import com.example.Library.Shop.model.User;
import com.example.Library.Shop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class LoginHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String username = authentication.getName();


        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));


        String role = user.getRole();

        if ("ROLE_ADMIN".equalsIgnoreCase(role)) {
            response.sendRedirect("/admin/adminForm");
        } else if ("ROLE_USER".equalsIgnoreCase(role)) {
            response.sendRedirect("/?id=" + user.getId());
        } else {

            response.sendRedirect("/access-denied");
        }
    }
}
