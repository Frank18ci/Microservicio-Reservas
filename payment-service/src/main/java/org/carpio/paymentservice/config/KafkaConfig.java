package org.carpio.paymentservice.config;

import org.carpio.paymentservice.model.Order;
import org.carpio.paymentservice.service.PaymentProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {
    private final PaymentProcessor paymentProcessor;

    public KafkaConfig(PaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }
    @Bean
    public DefaultErrorHandler errorHandler() {
        FixedBackOff backOff = new FixedBackOff(0L, 1);
        return new DefaultErrorHandler((record, ex) -> {
            Order order = (Order) record.value();
            paymentProcessor.paymentFallBack(order, ex);
        }, backOff);
    }

}
