package com.e_commerce.order_processing.payments;

import com.e_commerce.order_processing.orders.PaymentDetails;
import com.stripe.exception.*;

public interface PaymentGatway {
    public String chargeNewCard(PaymentDetails paymentDetails , float amount) throws PaymentHandlingException, APIConnectionException, APIException, AuthenticationException, InvalidRequestException, CardException;
}
