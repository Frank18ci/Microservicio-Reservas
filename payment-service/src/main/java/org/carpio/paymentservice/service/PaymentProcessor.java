package org.carpio.paymentservice.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.carpio.paymentservice.model.Order;
import org.carpio.paymentservice.model.Payment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentProcessor {
    private final KafkaTemplate<String, Payment> kafkaTemplate;
    private final Random random = new Random();

    @KafkaListener(topics = "orders", groupId = "payment-group")
    @CircuitBreaker(name = "paymentCB", fallbackMethod = "paymentFallBack")

    public void processPayment(Order order) {
        System.out.println("Procesando pago de: " + order);
        System.out.println("Processing order: " + order.getOrderId());
        if(random.nextInt(10) < 7){
            throw new RuntimeException("Payment processing failed for order: " + order.getOrderId());
        } else {
            System.out.println("Payment processed for order: " + order.getOrderId());
        }
    }
    public void paymentFallBack(Order order, Throwable t){
        Payment payment = new Payment(order.getOrderId(), order.getUserId(), "FAILED");
        kafkaTemplate.send("payments", payment.getOrderId(), payment);
    }
}
