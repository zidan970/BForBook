package com.alta.e_commerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alta.e_commerce.entities.Order;
import com.alta.e_commerce.entities.Payment;
import com.alta.e_commerce.entities.User;
import com.alta.e_commerce.models.CallbackRequest;
import com.alta.e_commerce.models.PaymentRequest;
import com.alta.e_commerce.models.PaymentResponse;
import com.alta.e_commerce.models.WebResponse;
import com.alta.e_commerce.services.CartService;
import com.alta.e_commerce.services.OrderService;
import com.alta.e_commerce.services.PaymentService;

@RestController
public class PaymentController {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping(
            path = "/payments",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PaymentResponse> create(@RequestBody PaymentRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id: " + genzaiNoShiyousha.getUserId());

        // find cart with current user id! if there is one, then get the cart id!
        String cartId = cartService.findOrCreateCart(genzaiNoShiyousha.getUserId());
        System.out.println("your cart id: " + cartId);

        // find order with current cart id! if there is one, then get the order id!
        Order order = orderService.findOrderId(cartId);
        System.out.println("your order id: " + order.getOrderId());

        request.setOrderId(order.getOrderId());
        request.setTotalAmount(200000); //temporary
        request.setVaNumber("754957710063396131"); //temporary

        PaymentResponse paymentResponse = paymentService.create(request, genzaiNoShiyousha.getUserId());
        return WebResponse.<PaymentResponse>builder()
                .message("this is your payment detail:")
                .data(paymentResponse)
                .build();
    }

    @PostMapping(
            path = "/callback",
            //consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> callback(@RequestBody CallbackRequest request) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User genzaiNoShiyousha = (User) authentication.getPrincipal();
        System.out.println("your id in callback: " + genzaiNoShiyousha.getUserId());

        // find order with current order id! if there is one, then get it!
        Order order = orderService.findOrder(request.getOrderId());
        System.out.println("your order id: " + order.getOrderId());

        Payment payment = paymentService.findPayment(order.getOrderId());

        paymentService.stockChange(order, payment);
        
        return WebResponse.<String>builder()
                .message("your payment is successful")
                .build();        
    }
}
            
                                                                                                                         
                
                
                    