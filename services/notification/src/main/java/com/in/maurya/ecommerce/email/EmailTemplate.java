package com.in.maurya.ecommerce.email;

import lombok.Getter;

public enum EmailTemplate {

    PAYMENT_CONFIRMATION("payment-confirmation.html", "Payment Successfully Processed"),

    ORDER_CONFIRMATION("order-confirmation.html", "Order Confirmation")
    ;

    @Getter
    private final String template;

    @Getter
    private final String subject;

    EmailTemplate(String template, String subject) {
        this.template = template;
        this.subject = subject;
    }

}
