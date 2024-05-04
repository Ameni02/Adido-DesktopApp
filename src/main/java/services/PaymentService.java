package services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import models.Commande;
import models.Panier;

public class PaymentService {

    public PaymentService() {
        Stripe.apiKey = System.getenv("sk_test_51Onn4mF2QIpG0aFFUoKvdL3MTyLXR7sKDAxlVZAgMiuDvUgmdROvT3Hfgb3t9jB0F3Fmliyn79OU90eizj41yeBL00c7gYb3gk");
    }

    public boolean processPayment(Panier panier) {
        long amount = Math.round(panier.getPrixTotal() * 100);
        String token = getPaymentToken();

        try {
            Charge charge = Charge.create(
                    new ChargeCreateParams.Builder()
                            .setAmount(amount) // Montant en centimes
                            .setCurrency("usd") // Devise
                            .setSource(token) // Token de carte de crédit
                            .build()
            );
            return true;
        } catch (StripeException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getPaymentToken() {
        // Implement logic to get the payment token
        // For now, return a dummy token
        return "tok_visa";
    }
}