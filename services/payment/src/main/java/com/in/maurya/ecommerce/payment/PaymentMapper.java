package com.in.maurya.ecommerce.payment;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentMapper {


    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .id(paymentRequest.id())
                .amount(paymentRequest.amount())
                .paymentMethod(paymentRequest.paymentMethod())
                .orderId(paymentRequest.orderId())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }
}
