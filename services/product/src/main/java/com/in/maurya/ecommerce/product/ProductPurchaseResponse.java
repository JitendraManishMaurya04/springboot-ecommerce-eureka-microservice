package com.in.maurya.ecommerce.product;

import java.math.BigDecimal;

public record ProductPurchaseResponse(

        Integer productId,

        String name,

        String description,

        BigDecimal price,

        double quantity
) {
}
