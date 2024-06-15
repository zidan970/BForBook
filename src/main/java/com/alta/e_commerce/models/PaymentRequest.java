package com.alta.e_commerce.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

    private String orderId;
    private float totalAmount;
    private String bankAccount;
    private String vaNumber;
    private String status;
    private Date createdAt;
    private Date updatedAt;
}
