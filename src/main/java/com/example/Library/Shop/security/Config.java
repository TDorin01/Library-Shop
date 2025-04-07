package com.example.Library.Shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(c -> c.disable())
                .formLogin(form -> form
                        .loginPage("/loginForm")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/loginForm?error=true")
                        .permitAll()
                       )
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/registerForm").permitAll()
                .requestMatchers("/registerUser").permitAll()
                .anyRequest().authenticated()

                );
        return http.build();
    }

    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        return  new BCryptPasswordEncoder();
    }


}
