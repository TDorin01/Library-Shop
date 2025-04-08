package com.example.Library.Shop.service;


import com.example.Library.Shop.model.Users;
import com.example.Library.Shop.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String role = user.getRole();
        int userId = user.getId();

        if ("ROLE_ADMIN".equals(role)) {
            response.sendRedirect("/admin/listBooks");
        } else if ("ROLE_USER".equals(role)) {
            response.sendRedirect("/?id=" + userId);
        }
    }
}
