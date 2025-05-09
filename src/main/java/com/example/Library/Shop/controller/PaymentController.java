package com.example.Library.Shop.controller;

import com.example.Library.Shop.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private StripeService stripeService;

    @GetMapping("/paymentCard")
    public String showPaymentForm() {
        return "paymentCard";
    }

    @PostMapping("/processCardPayment")
    public String processPayment(@RequestParam String stripeToken, Model model) {
        try {
            Charge charge = stripeService.charge(stripeToken, 5000, "ron", "Plată card");
            model.addAttribute("chargeId", charge.getId());
            return "order/orderSuccedForm";
        } catch (StripeException e) {
            model.addAttribute("error", e.getMessage());
            return "paymentFailed";
        }
    }
}

