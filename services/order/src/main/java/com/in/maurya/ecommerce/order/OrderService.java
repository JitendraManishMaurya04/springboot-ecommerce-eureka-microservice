package com.in.maurya.ecommerce.order;

import com.in.maurya.ecommerce.customer.CustomerClient;
import com.in.maurya.ecommerce.exception.BusinessException;
import com.in.maurya.ecommerce.kafka.OrderConfirmation;
import com.in.maurya.ecommerce.kafka.OrderProducer;
import com.in.maurya.ecommerce.orderLine.OrderLineRequest;
import com.in.maurya.ecommerce.orderLine.OrderLineService;
import com.in.maurya.ecommerce.payment.PaymentClient;
import com.in.maurya.ecommerce.payment.PaymentRequest;
import com.in.maurya.ecommerce.product.ProductClient;
import com.in.maurya.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final PaymentClient paymentClient;
    private final OrderMapper orderMapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest orderRequest) {
        //check the customer via customer-ms (OpenFeignClient)
        var customer = customerClient.findByCustomerId(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer present with the provided customerId: "+orderRequest.customerId()));

        log.info("Customer Found In DB: "+ customer.firstname()+" "+customer.lastname());
        //purchase the product via product-ms (Rest-Template)
        var purchasedProducts = productClient.purchasedProducts(orderRequest.products());

        //persist order
        var order = orderRepository.save(orderMapper.toOrder(orderRequest));

        //persist orderLine
       for(PurchaseRequest purchaseRequest: orderRequest.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
       }

        //start payment process via payment-ms
        paymentClient.requestOrderPayment(
                new PaymentRequest(
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        order.getId(),
                        order.getReference(),
                        customer)
        );

        //send the order confirmation to notification-ms via kafka
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
         );
        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID:: %d", orderId)));
    }
}
