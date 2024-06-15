package com.alta.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {

    private String paymentId;
    private String orderId;
    private float totalAmount;
    private String bankAccount;
    private String vaNumber;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
