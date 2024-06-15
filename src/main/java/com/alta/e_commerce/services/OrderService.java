package com.alta.e_commerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.alta.e_commerce.entities.Cart;
import com.alta.e_commerce.entities.Order;
import com.alta.e_commerce.models.OrderRequest;
import com.alta.e_commerce.models.OrderResponse;
import com.alta.e_commerce.repositories.CartRepository;
import com.alta.e_commerce.repositories.OrderRepository;
import java.util.*;

@Service
public class OrderService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public OrderResponse checkout(OrderRequest request){

        System.out.println("id cart in service: " + request.getCartId());

        // check whether the cart exists or not
        Cart cart = cartRepository.findByCartId(request.getCartId())
            .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"cart is not found"));

        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setCart(cart);
        order.setStatus("On Going");

        orderRepository.save(order);

        return OrderResponse.builder()
            .orderId(order.getOrderId())
            .cartId(order.getCart().getCartId())
            .status(order.getStatus())
            .build();
    }
}
