package controllers;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

public class PaymentProcessor {

    // Méthode pour effectuer un paiement avec Stripe
    public boolean processPayment(double amount, String token) {
        // Configuration de la clé secrète de l'API Stripe
        Stripe.apiKey = "sk_test_51PCTpzAITMBvnG134uDPYNnhJK2W4krUJMp9FijdxzuSIxslKA9ROyYMsxxbBJ7lQTYOsWR2eqSHQnCg73UYW1PA00aLyfeH9O";

        try {
            // Création de la charge
            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount((long) (amount * 100)) // Montant en centimes
                            .setCurrency("usd") // Devise
                            .setSource(token) // Token de carte de crédit
                            .build()
            );

            // Le paiement a réussi
            return true;
        } catch (StripeException e) {
            e.printStackTrace();
            // Le paiement a échoué
            return false;
        }
    }
}
