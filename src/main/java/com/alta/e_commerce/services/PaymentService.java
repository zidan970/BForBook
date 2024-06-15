package com.alta.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alta.e_commerce.entities.Cart;
import com.alta.e_commerce.entities.Order;
import com.alta.e_commerce.entities.Payment;
import com.alta.e_commerce.models.PaymentRequest;
import com.alta.e_commerce.models.PaymentResponse;
import com.alta.e_commerce.repositories.CartRepository;
import com.alta.e_commerce.repositories.OrderRepository;
import com.alta.e_commerce.repositories.PaymentRepository;

import java.util.*;

@Service
public class PaymentService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public PaymentResponse create(PaymentRequest request, String userId) {
        System.out.println("your order id in service: " + request.getOrderId());

        Order order = orderRepository.findById(request.getOrderId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order's not found"));

        Payment payment = new Payment();
        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setOrder(order);
        payment.setTotalAmount(request.getTotalAmount());
        payment.setBankAccount(request.getBankAccount());
        payment.setVaNumber(request.getVaNumber());
        payment.setStatus("pending");

        paymentRepository.save(payment);
        
        Cart cart = cartRepository.findByUserIdAndStatusOnGoing(userId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "cart's not found"));

        // update cart's status
        cart.setStatus("Selesai");
    
        return PaymentResponse.builder()
            .paymentId(payment.getPaymentId())
            .orderId(payment.getOrder().getOrderId())
            .totalAmount(payment.getTotalAmount())
            .bankAccount(payment.getBankAccount())
            .vaNumber(payment.getVaNumber())
            .status(payment.getStatus())
            .createdAt(payment.getCreatedAt())
            .updatedAt(payment.getUpdatedAt())
            .build();
    }
}
