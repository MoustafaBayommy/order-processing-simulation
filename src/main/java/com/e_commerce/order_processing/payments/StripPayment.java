package com.e_commerce.order_processing.payments;

import com.e_commerce.order_processing.orders.PaymentDetails;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class StripPayment implements PaymentGatway{
    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;


    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    @Override
    public String chargeNewCard(PaymentDetails paymentDetails, float amount) throws PaymentHandlingException, APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException {
        String cardToken=getPaymentToken(paymentDetails);
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", cardToken);
        Charge charge = Charge.create(chargeParams);
        return charge.getId();
    }

    public String getPaymentToken(PaymentDetails paymentDetails) throws APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException {
        Map<String, Object> card = new HashMap<>();
        card.put("number",paymentDetails.getCardNumber());
        card.put("exp_month", paymentDetails.getExpMonth());
        card.put("exp_year", paymentDetails.getExpYear());
        card.put("cvc", paymentDetails.getCvc());
        Map<String, Object> params = new HashMap<>();
        params.put("card", card);
        Token token = Token.create(params);
        return token.getId();
    }
}
