package com.in.maurya.ecommerce.kafka;

import com.in.maurya.ecommerce.customer.CustomerResponse;
import com.in.maurya.ecommerce.order.PaymentMethod;
import com.in.maurya.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(

    String orderReference,
    BigDecimal totalAmount,
    PaymentMethod paymentMethod,
    CustomerResponse customer,
    List<PurchaseResponse> products

) {
}
