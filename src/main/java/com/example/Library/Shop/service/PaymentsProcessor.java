package com.example.Library.Shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentsProcessor {
    @Autowired
    CreditCardService creditCardService;

    @Autowired
    CashPaymentService cashPaymentService;

    public String processPayment(String method) {
        return switch (method) {
            case "card" -> creditCardService.process();
            case "cash" -> cashPaymentService.process();
            default -> "invalid processing method";
        };
    }
}
