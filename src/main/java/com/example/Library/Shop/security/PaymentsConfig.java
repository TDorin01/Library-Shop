package com.example.Library.Shop.security;


import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "payments")
@Getter
public class PaymentsConfig {
    private String currency;
    private char delimiter;
}
