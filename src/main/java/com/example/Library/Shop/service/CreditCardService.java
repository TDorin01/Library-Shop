package com.example.Library.Shop.service;


import com.example.Library.Shop.security.PaymentsConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreditCardService {
    private final PaymentsConfig paymentsConfig;

    @Value("${my.custom.message}")
    private String message;


    public String process() {
        return message + paymentsConfig.getCurrency();
    }
}
