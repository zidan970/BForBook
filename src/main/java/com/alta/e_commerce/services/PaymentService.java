package com.alta.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alta.e_commerce.entities.Cart;
import com.alta.e_commerce.entities.Order;
import com.alta.e_commerce.entities.Payment;
import com.alta.e_commerce.entities.Product;
import com.alta.e_commerce.models.PaymentRequest;
import com.alta.e_commerce.models.PaymentResponse;
import com.alta.e_commerce.models.StockChangeDTO;
import com.alta.e_commerce.repositories.CartRepository;
import com.alta.e_commerce.repositories.OrderRepository;
import com.alta.e_commerce.repositories.PaymentRepository;
import com.alta.e_commerce.repositories.ProductRepository;

import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

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

    @Transactional
    public Payment findPayment(String orderId) {
        System.out.println("your order id in service: " + orderId);

        Payment payment = paymentRepository.findByOrder_OrderId(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "payment's not found"));

        return payment;
    }

    @Transactional
    public void stockChange(Order order, Payment payment) {
        System.out.println("your order id in stockChange: " + order.getOrderId());

        // update order's status
        order.setStatus("Selesai");
        orderRepository.save(order);

        // update payment's status
        payment.setStatus("settlement");
        paymentRepository.save(payment);

        // update stock
        List<StockChangeDTO> listData = paymentRepository.findStockQuantityByOrderId(order.getOrderId());

        for (StockChangeDTO list : listData) {
            System.out.println("stock: " + list.getStock() + ", quantity: " + list.getQuantity());
        }

        for (StockChangeDTO data : listData) {
            int currentStock = data.getStock();
            int quantity = data.getQuantity();
            int newStock = currentStock - quantity;

            data.setStock(newStock);

            Product product = productRepository.findById(data.getProductId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product's not found"));

            product.setStock(data.getStock());
            productRepository.save(product);
        }
    }
}
