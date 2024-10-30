package com.in.maurya.ecommerce.notification;

import com.in.maurya.ecommerce.payment.PaymentMethod;
import com.in.maurya.ecommerce.payment.PaymentRequest;

import java.math.BigDecimal;

public record PaymentNotificationRequest(

        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstName,
        String customerLastName,
        String customerEmail

) {
}
