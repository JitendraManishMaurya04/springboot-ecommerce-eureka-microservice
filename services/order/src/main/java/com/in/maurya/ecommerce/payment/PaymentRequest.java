package com.in.maurya.ecommerce.payment;

import com.in.maurya.ecommerce.customer.CustomerResponse;
import com.in.maurya.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,

        PaymentMethod paymentMethod,

        Integer orderId,

        String orderReference,

        CustomerResponse customer
) {

}
