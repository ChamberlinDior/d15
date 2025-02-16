package com.nova.colis.controller;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/colis")
public class PaymentController {

    // Initialisation de Stripe avec votre clé secrète (à NE JAMAIS exposer côté client)
    static {
        Stripe.apiKey = "sk_test_51QsrY6Gs7nPPcdDWSg6BUqxGmuBeXxaoelspkWvyflasNS67lvJ1pyAwHYaDTPKbfnYy8SzgPgvZp7HjTvmbzX1900Iio0BNXI";
    }

    /**
     * Endpoint pour créer un PaymentIntent.
     * Le frontend envoie le montant (en centimes) et la devise.
     * Le PaymentIntent est créé et le clientSecret est renvoyé.
     */
    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, Object>> createPaymentIntent(@RequestBody Map<String, Object> paymentData) {
        try {
            // Extraction du montant et de la devise depuis la requête
            long amount = Long.parseLong(paymentData.get("amount").toString());
            String currency = paymentData.get("currency").toString();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency(currency)
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            Map<String, Object> responseData = new HashMap<>();
            responseData.put("clientSecret", paymentIntent.getClientSecret());

            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            e.printStackTrace();
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorData);
        }
    }
}
