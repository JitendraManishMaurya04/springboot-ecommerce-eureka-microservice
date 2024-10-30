package com.in.maurya.ecommerce.orderLine;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(

        Integer id,

        Integer orderId,

        Integer productId,

        double quantity
) {
}
