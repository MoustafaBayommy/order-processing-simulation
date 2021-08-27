package com.e_commerce.order_processing.payments;

import com.e_commerce.order_processing.orders.PaymentDetails;
import com.stripe.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentGatway paymentGatway;



    public void deductOrderValue(PaymentDetails paymentDetails,float amount){
        try {
            paymentGatway.chargeNewCard(paymentDetails, amount);
        } catch (APIConnectionException e) {
            e.printStackTrace();
        } catch (APIException e) {
            e.printStackTrace();
        } catch (AuthenticationException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (CardException e) {
            e.printStackTrace();
        }
    }
}
