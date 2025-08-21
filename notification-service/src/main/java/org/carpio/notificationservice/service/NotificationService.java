package org.carpio.notificationservice.service;

import org.carpio.notificationservice.model.Payment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @KafkaListener(topics = "payments", groupId = "notification-group")
    public void sendNotification(Payment payment) {
        if("COMPLETED".equals(payment.getStatus())) {
            System.out.println("Notification: Payment completed for order " + payment.getOrderId());
        } else if ("FAILED".equals(payment.getStatus())) {
            System.out.println("Notification: Payment failed for order " + payment.getOrderId());
        } else {
            System.out.println("Notification: Payment status " + payment.getStatus() + " for order " + payment.getOrderId());
        }
    }
}
