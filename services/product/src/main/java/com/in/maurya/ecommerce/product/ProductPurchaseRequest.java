package com.in.maurya.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import org.hibernate.id.IntegralDataTypeHolder;

public record ProductPurchaseRequest(

        @NotNull(message = "Product is mandatory")
        Integer productId,

        @NotNull(message = "Quantity is mandatory")
        double quantity
) {
}
