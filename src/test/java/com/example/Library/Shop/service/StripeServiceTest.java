package com.example.Library.Shop.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StripeServiceTest {

    @Autowired
    private StripeService stripeService;

    @Test
    void shouldChargeCardSuccessfully() throws StripeException {

        String testToken = "tok_visa";

        Charge charge = stripeService.charge(testToken, 1000, "ron", "Plată de test");

        assertThat(charge).isNotNull();
        assertThat(charge.getAmount()).isEqualTo(1000);
        assertThat(charge.getCurrency()).isEqualTo("ron");
        assertThat(charge.getPaid()).isTrue();
    }
}