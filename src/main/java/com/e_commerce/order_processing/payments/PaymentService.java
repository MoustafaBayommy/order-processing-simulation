package com.e_commerce.order_processing.payments;

import com.e_commerce.order_processing.orders.PaymentDetails;
import com.e_commerce.order_processing.util.HttpException;
import com.stripe.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    PaymentGatway paymentGatway;



    public void deductOrderValue(PaymentDetails paymentDetails,float amount){
        try {
            paymentGatway.chargeNewCard(paymentDetails, amount);
        } catch (Exception e) {
        throw new HttpException(HttpStatus.FAILED_DEPENDENCY,"payment failed message :  "+e.getMessage());
        }
    }
}
