package com.in.maurya.ecommerce.order;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {
        if (orderRequest == null) {
            return null;
        }
        return Order.builder()
                .id(orderRequest.id())
                .customerId(orderRequest.customerId())
                .reference(orderRequest.reference())
                .totalAmount(orderRequest.amount())
                .paymentMethod(orderRequest.paymentMethod())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
