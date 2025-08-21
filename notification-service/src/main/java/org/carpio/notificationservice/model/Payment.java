package org.carpio.notificationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String orderId;
    private String userId;
    private String status; // e.g., "PENDING", "COMPLETED", "FAILED"
}
