package com.example.Library.Shop.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Charge charge(String token, int amount, String currency, String description) throws StripeException {
        ChargeCreateParams params = ChargeCreateParams.builder()
                .setAmount((long) amount)
                .setCurrency(currency)
                .setDescription(description)
                .setSource(token)
                .build();
        return Charge.create(params);
    }
}
